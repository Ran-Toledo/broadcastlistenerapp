# Use the official lightweight Python image
FROM python:3.11-slim

# Set working directory
WORKDIR /app

# Copy files
COPY . .

# Install dependencies
RUN pip install -r requirements.txt

# Expose the port Flask will run on
EXPOSE 5000

# Start the app
CMD ["python", "app.py"]
