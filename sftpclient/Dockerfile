# ---------- runtime image ----------
FROM eclipse-temurin:21-jre-jammy


# Create non-root user (security best practice)
RUN useradd -ms /bin/bash appuser

WORKDIR /app

# Copy the fat jar
COPY target/sftpclient-0.0.1-SNAPSHOT.jar app.jar
# Create local directories used by the app
RUN mkdir -p local/in local/out \
    && chown -R appuser:appuser /app

USER appuser

# JVM options can be overridden at runtime
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# Run the app
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]