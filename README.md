# 1. Docker image build (example)
    docker build -t nahwu2/kafka-java-benchmark:0.0.1 .

# 2. Deployment
### Deploy Kafka + Zookeeper + Java Application

    docker compose up -d

### Bring down Kafka + Zookeeper + Java Application

    docker compose down

### [Optional] Bring down Java Application only

    docker compose stop kafka-java-benchmark-app


# 3. Logs

    docker logs kafka-java-benchmark-kafka-java-benchmark-app-1 --tail 500

# X.1 Concepts

# X.2 Documentations + Articles
