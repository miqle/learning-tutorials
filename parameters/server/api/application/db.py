import sqlite3

import click
from flask import current_app, g
from flask.cli import with_appcontext
from flask_sqlalchemy import SQLAlchemy
import uuid

sqldb = SQLAlchemy()

@click.command('init-db')
@with_appcontext
def init_db_command():
    sqldb.create_all() 
    click.echo('Initialized the database.')

@click.command('drop-db')
@with_appcontext
def drop_all_command():
    sqldb.drop_all()
    click.echo('Drop the database.')

def init_app(app):
    sqldb.init_app(app)
    print('Add commands >> ')
    app.cli.add_command(init_db_command)
    app.cli.add_command(drop_all_command)
