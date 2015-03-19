__author__ = 'Jan'


class Const:
    #credentials
    email = "test02@ulticloud.com"
    login_url = "https://UltiCloud.com:5000/v2.0/"

    #networking
    inner_network_name = "working_network"
    outer_network_name = "public"
    router_name = "working_router"

    # create a keypair
    keypair_name = "ssh_keypair"

    # computing
    job_manager_image_name = "Ubuntu 12.04 cloudimg x86_64 2013-01-06"
    job_manager_name = "job_manager"

    worker_count = 3
    worker_image_name = "Ubuntu 12.04 cloudimg x86_64 2013-01-06"
    worker_name_template = "worker_manager_{0}"

    container_name = "common_container"