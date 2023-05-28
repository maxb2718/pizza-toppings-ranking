# Use the Nginx image from Docker Hub
FROM nginx:alpine

# Remove the default Nginx configuration file
RUN rm /etc/nginx/conf.d/default.conf

# Replace it with our custom Nginx configuration file
COPY nginx.conf /etc/nginx/conf.d/

# Copy the HTML file to the Nginx HTML directory
COPY index.html /usr/share/nginx/html/
