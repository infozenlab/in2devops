	$(document).tooltip({
		selector : "[title]",
		placement : "top",
		trigger : "focus",
		animation : true,
		show : {
			ready : false
		},
		hide : {
			event : false
		}
	});
	
	var freqData = {};

	$(document).ready(function() {
		var d = new Date();
		$("#endDate").val(d);
		for (var i = 1; i < 5; i++) {
			var m = 6 * i;
			var curmonth = d.getMonth();
			var qqq = curmonth - 6;
			d.setMonth(qqq);
			$("#" + m + "ago").val(d);
		}
		$('#updateGraphics').submit(function(e) {
			e.preventDefault();
			$.blockUI({
				css : {
					border : 'none',
					padding : '10px',
					backgroundColor : '#000',
					'-webkit-border-radius' : '10px',
					'-moz-border-radius' : '10px',
					opacity : .5,
					color : '#fff'
				}
			});
			data = $("#updateGraphics :input").filter(function(index, element) {
				return $(element).val() != "";
			}).serialize();
			// Get the data
			getMedEvents(data);
		});
	});

	function getMedEvents(data) {
		$.ajax({
			type : 'GET',
			url : "drug/summary",
			data : data,
			cache : false,
			success : function(msg) {
				$.unblockUI();
				var obj = msg;
				if (obj.success) {
					$("#warningRow").addClass("hidden");
					freqData = jQuery.makeArray(obj.drugFrequencies);
					dashboard('#dashboard', freqData);
				} else {
					var om = obj.message;
					if(om == ""){
						om="Invalid request";
					}
					$("#dashboard").empty();
					$("#dashboard-helptext").addClass("hidden");
					$("#warningMess").text(om);
					$("#warningRow").css('opacity', '1').css('display', '').removeClass("hidden");
					window.setTimeout(function() {
					    $("#warningRow").fadeTo(500, 0).slideUp(500, function(){
					        $(this).addClass("hidden"); 
					    });
					}, 3000);
				}
			},
			error : function(e) {
				$.unblockUI();
				if (window.console) {
					console.log(e);
				}
				try {
					var resp = JSON.parse(e.responseText);
					alert(resp.message);
				} catch (e) {
				}

			}
		});
	}

	$("#drugName").autocomplete({
		delay : 500,
		autoFocus : true,
		source : function(req, res) {
			$.ajax({
				url : "drug/names",
				dataType : "json",
				data : {
					drugName : req.term,
				},
				success : function(data) {
					res(data);
				}
			})
		}
	});

	var theColors = [ "#00a2a2", "#ff0000", "#ddff00", "#00a200", "#ffa200",
			"#510051", "#bb8e68", "#005dff", "#ff00dd", "#b2e3e3", "#810000",
			"#66c766", "#ffd07f", "#be4dff", "#664428", "#FFFF66", "#192947",
			"#ffa5f3", "#c5b2c5", "#400040", "#ff6666", "#ceb428", "#a8a8a8",
			"#545454", "#667084", "#B5FF43", "#364C14", "#8992f0", "#D7D7D7" ];

	function GetHeaders(obj) {
		var cols = new Array();
		for ( var i in obj) {
			var df = obj[i];
			for ( var feq in df.frequency) {
				if (cols.indexOf(feq) == -1) {
					cols.push(feq);
				}
			}
		}
		return cols;
	}

	function dashboard(id, fData) {
		$("#dashboard-helptext").insertAfter("#dashboard");
		$(id).empty();
		// theReactions is an array of symptoms specific to data set
		var theReactions = GetHeaders(freqData);
		var arrayLength = theReactions.length;
		// symptomColors is an object containing key/value pairs of
		// symptom:randomly generated color
		var symptomColors = {};
		// generate key/value pairts for symptomColors
		for (var i = 0; i < arrayLength; i++) {
			symptomColors[theReactions[i]] = theColors[i];
		}

		var barColor = '#0051a2';

		// ----------------------place to use dynamic variables
		function segColor(c) {
			return symptomColors[c];
		}

		// function to handle histogram.
		function histoGram(fD) {
			var hG = {}, hGDim = {
				t : 60,
				r : 0,
				b : 30,
				l : 0
			};
			hGDim.w = 500 - hGDim.l - hGDim.r, hGDim.h = 300 - hGDim.t
					- hGDim.b;

			// create svg for histogram.
			var hGsvg = d3.select(id).append("svg").attr("class", "bars").attr(
					"width", hGDim.w + hGDim.l + hGDim.r + 20).attr("height",
					hGDim.h + hGDim.t + hGDim.b + 50).append("g").attr(
					"transform", "translate(" + hGDim.l + "," + hGDim.t + ")");

			// create function for x-axis mapping.
			var x = d3.scale.ordinal().rangeRoundBands([ 0, hGDim.w ], 0.1)
					.domain(fD.map(function(d) {
						return d[0];
					}));

			// Add x-axis to the histogram svg.
			hGsvg.append("g").attr("class", "x axis").attr("transform",
					"translate(0," + hGDim.h + ")").call(
					d3.svg.axis().scale(x).orient("bottom")).selectAll("text")
					.attr("y", 2).attr("x", 9).attr("dy", ".35em").style(
							"text-anchor", "start").style("font-size", "13px")
					.attr("transform", "rotate(55)");

			// Create function for y-axis map.
			var y = d3.scale.linear().range([ hGDim.h, 0 ]).domain(
					[ 0, d3.max(fD, function(d) {
						return d[1];
					}) ]);

			// Create bars for histogram to contain rectangles and freq labels.
			var bars = hGsvg.selectAll(".bar").data(fD).enter().append("g")
					.attr("class", "bar");

			// create the rectangles.
			bars.append("rect").attr("x", function(d) {
				return x(d[0]);
			}).attr("y", function(d) {
				return y(d[1]);
			}).attr("width", x.rangeBand()).attr("height", function(d) {
				return hGDim.h - y(d[1]);
			}).attr('fill', barColor).on("mouseover", mouseover)// mouseover is
																// defined
																// below.
			.on("mouseout", mouseout);// mouseout is defined below.

			// Create the frequency labels above the rectangles.
			bars.append("text").text(function(d) {
				return d3.format(",")(d[1])
			}).attr("x", function(d) {
				return x(d[0]) + x.rangeBand() / 2;
			}).attr("y", function(d) {
				return y(d[1]) - 5;
			}).attr("text-anchor", "middle");

			function mouseover(d) { // utility function to be called on
									// mouseover.
				// filter for selected state.
				var st = fData.filter(function(s) {
					return s.date == d[0];
				})[0];

				nD = theReactions.map(function(data) {
					var fff = st.frequency[data];
					if (!(fff == 0)) {
						$("#" + data).addClass("highlight");
						if (data == "COUNTRY NOT SPECIFIED") {
							$("#CCNNSS").addClass("highlight");
						}
					}
					return {
						type : data,
						freq : fff
					};
				});

				// call update functions of pie-chart and legend.
				pC.update(nD);
				leg.update(nD);
			}

			function mouseout(d) { // utility function to be called on
									// mouseout.
				// reset the pie-chart and legend.
				pC.update(tF);
				leg.update(tF);
				$(".categoryRow").removeClass("highlight");
			}

			// create function to update the bars. This will be used by
			// pie-chart.
			hG.update = function(nD, color) {
				// alert(JSON.stringify(nD));
				// update the domain of the y-axis map to reflect change in
				// frequencies.
				y.domain([ 0, d3.max(nD, function(d) {
					return d[1];
				}) ]);

				// Attach the new data to the bars.
				var bars = hGsvg.selectAll(".bar").data(nD);

				// transition the height and color of rectangles.
				bars.select("rect").transition().duration(500).attr("y",
						function(d) {
							return y(d[1]);
						}).attr("height", function(d) {
					return hGDim.h - y(d[1]);
				}).attr("fill", color);

				// transition the frequency labels location and change value.
				bars.select("text").transition().duration(500).text(
						function(d) {
							return d3.format(",")(d[1])
						}).attr("y", function(d) {
					return y(d[1]) - 5;
				});
			}
			return hG;
		}
		// -----------------------------------------------------------------------------
		// function to handle pieChart.
		function pieChart(pD) {
			var pC = {}, pieDim = {
				w : 250,
				h : 250
			};
			pieDim.r = Math.min(pieDim.w, pieDim.h) / 2;

			// create svg for pie chart.
			var piesvg = d3.select(id).append("svg").attr("class", "pie").attr(
					"width", pieDim.w).attr("height", pieDim.h + 30)
					.append("g").attr(
							"transform",
							"translate(" + pieDim.w / 2 + "," + pieDim.h / 2
									+ ")");

			// create function to draw the arcs of the pie slices.
			var arc = d3.svg.arc().outerRadius(pieDim.r - 10).innerRadius(0);

			// create a function to compute the pie slice angles.
			var pie = d3.layout.pie().sort(null).value(function(d) {
				return d.freq;
			});

			// Draw the pie slices.
			piesvg.selectAll("path").data(pie(pD)).enter().append("path").attr(
					"d", arc).each(function(d) {
				this._current = d;
			}).style("fill", function(d) {
				return segColor(d.data.type);
			}).on("mouseover", mouseover).on("mouseout", mouseout);

			// create function to update pie-chart. This will be used by
			// histogram.
			pC.update = function(nD) {
				piesvg.selectAll("path").data(pie(nD)).transition().duration(
						500).attrTween("d", arcTween);
			}
			// Utility function to be called on mouseover a pie slice.
			function mouseover(d) {
				$(".categoryRow").each(function() {
					var ddt = d.data.type;
					if (ddt == "COUNTRY NOT SPECIFIED") {
						ddt = "CCNNSS";
					}
					if ($(this).attr("id") == ddt) {
						$(this).addClass("highlight");
					}
				})
				// call the update function of histogram with new data.
				hG.update(fData.map(function(v) {
					var temp = v.frequency[d.data.type];
					if (typeof temp === 'undefined') {
						temp = 0;
					}
					return [ v.date, temp ];
				}), segColor(d.data.type));
			}
			// Utility function to be called on mouseout a pie slice.
			function mouseout(d) {
				$(".categoryRow").each(function() {
					$(this).removeClass("highlight");
				})
				// call the update function of histogram with all data.
				hG.update(fData.map(function(v) {
					return [ v.date, v.total ];
				}), barColor);
			}
			// Animating the pie-slice requiring a custom function which
			// specifies
			// how the intermediate paths should be drawn.
			function arcTween(a) {
				var i = d3.interpolate(this._current, a);
				this._current = i(0);
				return function(t) {
					return arc(i(t));
				};
			}
			return pC;
		}
		// ------------------------------------------------------------------------------------
		// function to handle legend.
		function legend(lD) {
			var leg = {};

			// create table for legend.
			var legend = d3.select(id).append("table").attr('class', 'legend');

			// create one row per segment.
			var tr = legend.append("tbody").selectAll("tr").data(lD).enter()
					.append("tr").attr("id", function(d) {
						return d.type;
					}).attr("class", "categoryRow")
					.on("click", mouseover9);

			// create the first column for each segment.
			tr.append("td").append("svg").attr("width", '16').attr("height",
					'16').append("rect").attr("width", '16').attr("height",
					'16').attr("fill", function(d) {
				return segColor(d.type);
			});

			// create the second column for each segment.
			tr.append("td").text(function(d) {
				return d.type;
			});

			// create the third column for each segment.
			tr.append("td").attr("class", 'legendFreq').text(function(d) {
				return d3.format(",")(d.freq);
			});

			// create the fourth column for each segment.
			tr.append("td").attr("class", 'legendPerc').text(function(d) {
				return getLegend(d, lD);
			});

			// Utility function to be used to update the legend.
			leg.update = function(nD) {
				// update the data attached to the row elements.
				var l = legend.select("tbody").selectAll("tr").data(nD);

				// update the frequencies.
				l.select(".legendFreq").text(function(d) {
					return d3.format(",")(d.freq);
				});

				// update the percentage column.
				l.select(".legendPerc").text(function(d) {
					return getLegend(d, nD);
				});
			}

			function getLegend(d, aD) { // Utility function to compute
										// percentage.
				return d3.format("%")(d.freq / d3.sum(aD.map(function(v) {
					return v.freq;
				})));
			}

//			function mouseover9() {
//				if ($(this).hasClass("highlight")) {
//					$(this).toggleClass("highlight");
//
//				} else {
//					var selectedId = $(this).attr("id");
//					$(".categoryRow").each(function() {
//						if ($(this).hasClass("highlight")) {
//							$(this).toggleClass("highlight");
//						}
//					});
//					$(this).toggleClass("highlight");
//				}
//
//			}
//
//			return leg;
//		}
		
		//new function to update the update graphics when clicking categoryRow in legend
		function mouseover9(d){
			if (!$(this).hasClass("highlight")) {
				$(".categoryRow").each(function() {
					if ($(this).hasClass("highlight")) {
						$(this).toggleClass("highlight");
					}
				});
				$(this).toggleClass("highlight");
		    hG.update(fData.map(function(v){
		        return [v.date,v.frequency[d.type]];}),segColor(d.type));
		    //disable mouseover and mouseout if country is selected in legend
		    var pieSVG = document.querySelector('.pie');
		    pieSVG.setAttribute('class', 'nomo pie');
		    var barsSVG = document.querySelector('.bars');
		    barsSVG.setAttribute('class', 'nomo bars');
			}
			else{
				$(this).toggleClass("highlight");
				 hG.update(fData.map(function(v){
		             return [v.date,v.total];}), barColor);
				 //enable mouseover and mouseout when country is deselected in legend
				 var mySVG = document.querySelector('.pie');
				    mySVG.setAttribute('class', 'pie');
				    var barsSVG = document.querySelector('.bars');
				    barsSVG.setAttribute('class', 'bars');
			}
		}

		return leg;
		}

		// calculate total frequency by segment for all state.
		// ----------------------place to use dynamic variables
		var tF = theReactions.map(function(d) {
			return {
				type : d,
				freq : d3.sum(fData.map(function(t) {
					return t.frequency[d];
				}))
			};
		});

		// calculate total frequency by state for all segment.
		var sF = fData.map(function(d) {
			return [ d.date, d.total ];
		});

		var hG = histoGram(sF), // create the histogram.
		pC = pieChart(tF), // create the pie-chart.
		leg = legend(tF); // create the legend.
		var www = $(".categoryRow").length;
		$("#dashboard-helptext").removeClass("hidden");
		$("[id='COUNTRY NOT SPECIFIED']").attr("id", "CCNNSS");
		$(".bars, .pie")
				.wrapAll(
						"<div id='divOne' class='col-sm-9 text-center'><div class='row' id='rowOne'></div></div>");
		$(".legend").wrapAll("<div id='divTwo' class='col-sm-3'></div>");
		$(".bars")
				.wrap(
						"<div id='divThree' class='col-sm-7 col-sm-offset-1 text-center'></div>");
		$(".pie").wrap("<div id='divFour' class='col-sm-4 text-center'></div>");
		if (www > 17) {
			$("#dashboard-helptext").insertAfter("#rowOne");
		}

	}