pipeline {
    agent any

    tools {
        jdk 'jdk-21'
        maven 'maven-3'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Deploy to Nexus') {
            steps {
                sh 'mvn deploy -DskipTests'
            }
        }

        stage('Deploy to Localhost') {
            steps {
                // Stop existing containers if running
                sh 'docker-compose down || true'

                // Build and start containers
                sh 'docker-compose up -d --build'
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