## Pactice Flask + uwsgi + nginx

### How to run
```
export FLASK_APP="app.server"
export FLASK_ENV="development"
flask run
```

### View document at https://docs.python.org/3/library/sqlite3.html

### Build docker compose
```
docker build . -t server_api:latest
docker-compose up
``` 


### python test
Python cannot find the module under test, webcount, because it is not
located in Python’s default module loading path.
You can fix this by adding the absolute path of your project’s root
directory to a file with the extension .pth into your virtualenv’s sitepackages directory. For example, if you use Python 3.5, and your virtualenv is in the directory venv/, you could put the absolute path into the file venv/lib/python3.5/site-packages/webcount.pth. Other methods of manipulating the “Python path” are discussed in the official Python documentation.