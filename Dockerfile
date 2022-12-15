FROM ubuntu:latest
RUN apt-get update
RUN apt-get install -y default-jre
RUN apt-get install -y apache2


COPY bash_script.sh bash_script.sh
COPY gateway/target/lab-gateway.jar lab-gateway.jar
COPY licenses/target/lab-licenses.jar lab-licenses.jar
COPY pilots/target/lab-pilots.jar lab-pilots.jar

COPY frontend /var/www/html
CMD ./bash_script.sh

EXPOSE 80
EXPOSE 8080