import os

from flask import Flask, jsonify
from flask_httpauth import HTTPBasicAuth
from werkzeug.security import generate_password_hash, check_password_hash
from api.routes.param_group import group_route
import uuid
from api.application import db

def create_app(test_config=None):
    # create and configure the app
    app = Flask(__name__, instance_relative_config=True)
    db_path = os.path.join('/app/data', 'flaskr.db');
    print('db_path: ' + db_path)
    app.config.from_mapping(
        SECRET_KEY='dev',
        DATABASE=db_path,
    )

    app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///' + db_path
    app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False

    auth = HTTPBasicAuth()

    users = {
        "john": generate_password_hash("hello"),
        "susan": generate_password_hash("bye")
    }

    @auth.verify_password
    def verify_password(username, password):
        if username in users:
            return check_password_hash(users.get(username), password)
        return False

    if test_config is None:
        # load the instance config, if it exists, when not testing
        app.config.from_pyfile('config.py', silent=True)
    else:
        # load the test config if passed in
        app.config.from_mapping(test_config)

    # ensure the instance folder exists
    try:
        os.makedirs(app.instance_path)
    except OSError:
        pass

    app.register_blueprint(group_route, url_prefix='/v1/params/groups')

    db.init_app(app)

    return app
