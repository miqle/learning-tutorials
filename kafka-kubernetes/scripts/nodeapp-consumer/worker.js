var kafka = require('kafka-node'),
    Consumer = kafka.Consumer,
    client = new kafka.KafkaClient({kafkaHost: 'kafka-deployment-0.kafka-service:9092'}),
    consumer = new Consumer(
        client,
        [
            { topic: 'kafka-kube-topic', partition: 0 }
        ],
        {
            autoCommit: true
        }
    );

consumer.on('message', async function(message) {
    console.log('here');
    console.log(
        'kafka-> ',
        message.value
    );
})
consumer.on('error', function(err) {
    console.log('error', err);
});
