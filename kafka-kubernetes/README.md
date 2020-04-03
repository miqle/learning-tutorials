# Tips

### Access a pod container

`kubectl exec -t -i <pod_id> bash`

### Create kafka topic
```
kafka-topics.bat --create \
--zookeeper 192.168.139.236:30000 \
--replication-factor 2 \
--partitions 3 \
--topic kafka-kube-topic
```

```
kafka-console-producer.bat \
--broker-list 172.17.184.156:30001 \
--topic kafka-kube-topic
```