from api.application.db import sqldb as db
from datetime import datetime
from marshmallow_sqlalchemy import ModelSchema
from marshmallow import fields

class ParamGroup(db.Model):
    __tablename__ = 'param_group'

    id = db.Column(db.String(50), primary_key=True)
    created_date = db.Column(db.DateTime(), default=datetime.utcnow)

    def __init__(self, id):
        self.id = id

    def create(self):
        db.session.add(self)
        db.session.commit()
        return self

class ParamGroupSchema(ModelSchema):
    class Meta(ModelSchema.Meta):
        model = ParamGroup
        sqla_session = db.session
    id = fields.String(required=True)