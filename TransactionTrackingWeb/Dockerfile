#### Stage 1: Build the angular application
FROM node:16.13.0 as build

# Configure the main working directory inside the docker image.
# This is the base directory
# commands.
WORKDIR /app

# Copy the package.json as well as the package-lock.json and install
# the dependencies. This is a separate step so the dependencies
# will be cached unless changes to one of those two files changed
COPY package*.json ./
RUN npm install

# Copy the main application
COPY . ./

# Build the application
RUN npm run build  --configuration=production

#### Stage 2, use the compiled app, ready for production with Nginx
FROM nginx

# Copy the angular build from Stage 1
ARG BUILT_PATH=/app/dist/TransactionTracker

COPY --from=build ${BUILT_PATH} /usr/share/nginx/html

# Copy our custom nginx config
COPY /nginx-custom.conf /etc/nginx/conf.d/default.conf

# Expose port 80 to the Docker host, so we can access it
# from the outside.
EXPOSE 80

ENTRYPOINT ["nginx","-g","daemon off;"]
