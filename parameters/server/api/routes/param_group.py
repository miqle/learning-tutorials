from flask import jsonify, Blueprint
import uuid
from api.models.param_group import ParamGroup, ParamGroupSchema
from werkzeug.exceptions import NotFound
from api.application.db import sqldb as db
from flask import Blueprint
from api.utils.response import response_with, SUCCESS_201

group_route = Blueprint('group_route', __name__)

@group_route.route('/', methods=['POST'])
def new_group():
    id = str(uuid.uuid4())
    pg = ParamGroup(id)
    result = ParamGroupSchema().dump(pg.create())
    print(result)
    ret = response_with(SUCCESS_201, result)
    return ret

@group_route.route('/<id>', methods=['GET'])
def get_group(id):
    pg = ParamGroup.query.get_or_404(id)
    return jsonify({'id': pg.id})
    

@group_route.route('/<id>', methods=['DELETE'])
def delete_group(id):
    pg = ParamGroup.query.get_or_404(id)
    db.session.delete(pg)
    db.session.commit()
    return jsonify({'id': pg.id})

@group_route.errorhandler(NotFound)
def handle_notfound(e):
    return jsonify({'message': str(e)}), 404