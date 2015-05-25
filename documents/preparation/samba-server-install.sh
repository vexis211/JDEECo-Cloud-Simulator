#!/bin/bash

# install samba -
sudo yum install samba samba-client samba-common

# to permit samba through firewall
# !!! firewall is probably not installed on cloud images
# firewall-cmd --permanent --zone=public --add-service=samba
# firewall-cmd --reload

cp /etc/samba/smb.conf /etc/samba/smb.conf.backup

sudo sh -c 'echo "[global]
workgroup = WORKGROUP
server string = Samba Server %v
netbios name = jobmanager
security = user
map to guest = bad user
dns proxy = no

#============================ Share Definitions ==============================
[simulationdrive]
path = /media/simulationdrive
browsable =yes
writable = yes
guest ok = yes
read only = no"\
>/etc/samba/smb.conf'

# enable services - samba, netbios
sudo systemctl enable smb.service
sudo systemctl enable nmb.service
# restart services
sudo systemctl restart smb.service
sudo systemctl restart nmb.service
# check if services are active
sudo systemctl is-active smb.service
sudo systemctl is-active nmb.service

# fix disk ownership
sudo chmod 777 /media/simulationdrive/
sudo chown -R nobody:nobody /media/simulationdrive/
sudo chcon -t samba_share_t /media/simulationdrive/

# restart samba service - for sure
sudo systemctl restart smb.service
