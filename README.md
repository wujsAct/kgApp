Knowlege graph demos:
included NER demo


#experiment enviroment   
server: nodejs   
front-end web: handlebars,boostrap,boostrap-table,jquery  

#
/ner is the route for named entity recognition
/stanfordParser? process the text into token array, posarray and chunk arrays   
we utilzie the OpenNLP to get the chunk array   
http: http://192.168.3.196:8000/NER? to get the final tagging results.   
