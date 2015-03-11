# coding=utf-8
__author__ = 'Jan'


class NovaClientConnector:
    @staticmethod
    def connect(user_name, api_key, proj_id, auth_url):
        from novaclient.v1_1 import client as nova_client

        nova_connection = nova_client.Client(
            username=user_name,
            api_key=api_key,
            project_id=proj_id,
            auth_url=auth_url,
        )
        return nova_connection


class NeutronClientConnector:
    @staticmethod
    def connect(user_name, password, tenant_name, auth_url):
        from neutronclient.v2_0 import client as neutron_client

        neutron_connection = neutron_client.Client(
            username=user_name,
            password=password,
            tenant_name=tenant_name,
            auth_url=auth_url,
        )
        return neutron_connection


class SwiftClientConnector:
    @staticmethod
    def connect(user, key, tenant_name, auth_url, auth_version):
        from swiftclient import client as swift_client

        swift_connection = swift_client.Connection(
            user=user,
            key=key,
            tenant_name=tenant_name,
            authurl=auth_url,
            auth_version=auth_version
        )
        return swift_connection