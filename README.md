# 1. Docker image build (example)
    docker build -t nahwu2/kafka-java-benchmark:0.0.1 .

# 2. Deployment
### Deploy Kafka + Zookeeper + Java Application

    docker compose up -d

### Bring down Kafka + Zookeeper + Java Application

    docker compose down

### [Optional] Bring down Java Application only

    docker compose stop kafka-java-benchmark-app


# 3. Deployment Configuration
## 3.1 Cassandra Database initial configuration
### 3.1.1 SSH into Docker container
    docker exec -it cassandra-4-exploration-cassandra-db-1 /bin/bash

# X.1 Concepts

# X.2 Documentations + Articles
