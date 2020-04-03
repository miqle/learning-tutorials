from kubernetes import client, config, kubernetes
import pprint

config.load_kube_config()

core_api = client.CoreV1Api(client.ApiClient(client.Configuration()))
ret = core_api.list_namespaced_pod("default", watch=False);

for i in ret.items:
    print(i)
    print("%s\t%s\t%s" % (i.status.pod_ip, i.metadata.namespace, i.metadata.name))
    


