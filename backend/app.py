from flask import Flask, request
import sys

app = Flask(__name__)

@app.route('/', methods=['POST'])
def receive_event():
    try:
        data = request.get_json(force=True)
        print("Received event:", data)
        sys.stdout.flush()
    except Exception as e:
        print("Failed to parse JSON:", e)
        sys.stdout.flush()

    return '', 200


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
