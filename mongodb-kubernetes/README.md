
## View volume
`docker volume inspect <volume name>`


`kubectl port-forward mongo-ss-2 27017:27017`

## replicaset
ssh to mongo node, any node
`kubectl exec -t -i mongo-ss-0 bash`

```


#1
mongo

#2
use admin
conf = {
  _id: "rs0",
  version: 1,
  members: [
	 { _id: 0, host : "mongo-ss-0.mongo-svc:27017" },
	 { _id: 1, host : "mongo-ss-1.mongo-svc:27017" },
	 { _id: 2, host : "mongo-ss-2.mongo-svc:27017" }
  ]
}
rs.initiate(conf)
```


