__author__ = 'Jan'


class Const:
    #credentials
    login = "skalicky"
    login_url = "https://openstack.d3s.mff.cuni.cz:5000/v2.0"
	project = "skalicky"
	
    # create a keypair
    ssh_keypair_name = "ssh"

    # computing
    worker_image_name = "CentOS-7-x86_64-GenericCloud-20140916_01 (8GB min)"
    worker_name_template = "Worker_{0}"