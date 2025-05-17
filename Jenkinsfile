pipeline {
    agent any

    tools {
        jdk 'jdk-21'
        maven 'maven-3'
    }

    stages {
        stage('Checkout') {
            steps {
            echo 'Checkout fase'
                checkout scm
            }
        }

        stage('Build') {
            steps {
            echo 'Build fase'
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Deploy to Localhost') {
            steps {
                echo 'Deploy on localhost'
                // Cria o container se não existir
                echo 'Creating container'
                sh '''
                if [ ! "$(docker ps -a -q -f name=ms-applications)" ]; then
                    docker run -d --name ms-applications -p 8085:8085 -w /app eclipse-temurin:21-jdk tail -f /dev/null
                fi
                '''
                // Copia o novo JAR para dentro do container
                sh 'docker cp target/*.jar ms-applications:/app/app.jar'

                // Mata o processo antigo do Java
                sh 'docker exec ms-applications pkill -f "java -jar" || true'

                // Inicia a aplicação novamente em background
                sh 'docker exec -d ms-applications java -jar /app/app.jar'
            }
        }
    }

    post {
        success {
            echo 'Deployment to localhost successful!'
        }
        failure {
            echo 'Deployment failed!'
        }
    }
}