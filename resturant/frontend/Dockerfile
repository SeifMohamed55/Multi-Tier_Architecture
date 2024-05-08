# Use Node.js as the base image
FROM node:22-alpine

# Set the working directory in the container
WORKDIR /usr/src/app

# Copy package.json and package-lock.json (if available) to the working directory
COPY package*.json ./

# Install Angular CLI globally
RUN npm install -g @angular/cli

# Install project dependencies
RUN npm install --force

# Copy the entire Angular project to the working directory
COPY . .

# Expose the port the app runs on
EXPOSE 4200

# Start the Angular application
CMD ["ng", "serve", "--host", "0.0.0.0"]