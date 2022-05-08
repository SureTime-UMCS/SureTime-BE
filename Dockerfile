FROM maven

WORKDIR /usr/src/mymaven

ADD pom.xml ./
RUN mvn verify clean --fail-never

COPY . .
RUN mvn spring-boot:run
