# -*- mode: ruby -*-

# vi: set ft=ruby :

VAGRANTFILE_API_VERSION = "2"

Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|

  config.vm.box = "identrix"
  config.vm.box_url = './identrix_box/identrix.box'
  config.ssh.username = "vagrant"
  config.ssh.password = "vagrant"

  # Begin appserver
  config.vm.define "fdademo1" do |fdademo1|
    fdademo1.vm.hostname = "fda-appserver"
    fdademo1.vm.network "private_network", type: "dhcp"
    fdademo1.vm.provider "vsphere" do |v|
        v.name="FDA-DemoServer"
        v.host = 'XX.XX.XX.XX'
        v.clone_from_vm = true
        v.template_name = 'PRODUCTION/boxes/AppServer-Base'
        v.user = 'INFOZN\\xxxxx'
        v.password = 'xxxxxx'
        v.insecure = true
        v.data_store_name = 'N1_T2_3P1_INFOZ_02'
        v.linked_clone = true
        v.compute_resource_name = 'esx15-03-03.infozn.local'
    end
  end
  # End appserver
end
