## PGR301 Exam Autumn 2020

[![Build Status](https://travis-ci.com/Hannarong98/PGR301-Exam-application.svg?token=DqMpxq41VWvgzW8Fy3oq&branch=master)](https://travis-ci.com/Hannarong98/PGR301-Exam-application)

## How to add secrets to travis

* Do the following if it's a value
    * `travis encrypt --pro MY_SECRET_ENV=SUPER_SECRET_VALUES --add`
    * Note if you are not using `travis-ci.com` then you will have to remove `--pro` flag
    * This will automatically add a secure environment variable in the `.travis.yml` file
    
* If it's a file you wish to encrypt do 
    * `travis encrypt-file --pro your-secret-file.json`
    * You will most likely get an openssl command as a result.
    * Edit the `.travis.yml` file and add the aforementioned result after `before_install:`
    * This will also add `your-secret-file.json.enc` file which you can safely push to your repository
    * Do `NOT` to push the `your-secret-file.json` un-encrypted file to the repository