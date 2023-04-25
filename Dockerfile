FROM lakruzz/lamj:latest

# ENV MYSQL_ROOT_PASSWORD=root

ENV PORT=8080
ENV MYSQL_PORT=3306

COPY src /src
COPY pom.xml /pom.xml
RUN set -ex; \
     mvn -f /pom.xml clean package; \
     mv /target/*.jar /app/; \
     rm -rf /target; \
     rm -rf /src; \
     rm -rf /pom.xml;

COPY src/mysql/init/* /docker-entrypoint-initdb.d/

EXPOSE $PORT $MYSQL_PORT

CMD set -eux; \
    lamj.init.sh; \
    java -jar /app/*.jar;

# Build like this:
# docker build  -t alphasolutions .

# Run like this:
# docker run -it --rm --name alphasolutions --pid=host -p 8081:8081 -p 3306:3306 -e password=1234 alphasolutions
#
#   - `docker run`: This command is used to run a container from an image.
#   - `-it`: This switch allocates a pseudo-TTY and opens an interactive terminal within the container.
#   - `--rm`: This switch removes the container automatically after it exits. (useful for development, but it resets the database every time)
#   - `--name AlphaSolutions`: This sets the name of the container to "superhero5".
#   - `--pid=host`: This runs the container in the host's PID namespace. (enable this if you  want to debug the container with a debugger or if you want to be able to stop the container with CTRL-C)
#   - `-p 8081:8081/8080:8080`: This maps port 8081 from the container to port 8081 on the host.
#   - `-p 3306:3306`: This maps port 3306 from the container to port 3306 on the host.
#   - `-e MYSQL_ROOT_PASSWORD/password=1234`: This environment variable sets the root password for MySQL 'root' is default.
#   - `AlphaSolutions`: This specifies the name of the image to run.