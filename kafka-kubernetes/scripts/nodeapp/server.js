const express = require('express')
const app = express()
const port = 3000
var kafka = require('kafka-node'),
    Producer = kafka.Producer,
    KeyedMessage = kafka.KeyedMessage,
    client = new kafka.KafkaClient({kafkaHost: 'kafka-deployment-0.kafka-service:9092'}),
    producer = new Producer(client),
    km = new KeyedMessage('key', 'message'),
    payloads = [
        { topic: 'kafka-kube-topic', messages: 'hi', partitions: 0 },
        { topic: 'kafka-kube-topic', messages: ['hello', 'world', km], partitions : 1}
    ];

producer.on('ready', function () {
    producer.send(payloads, function (err, data) {
        console.log("data: ");
        console.log(data);
        console.log("err: ");
        console.log(err);
    });
});

producer.on('error', function (err) {
    console.log("err: ");
    console.log(err);
})

app.get('/', (req, res) => res.send('Hello World!'))

app.listen(port, () => console.log(`Example app listening at http://localhost:${port}`))