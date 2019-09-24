#!/bin/bash
ES_ADDRESS=localhost
ES_USER=elastic
ES_PASSWORD=changeme

function _ilm()
{
 echo "Index life cycle setting"
 curl -u ${ES_USER}:${ES_PASSWORD} -v -XPUT "$ES_ADDRESS:9200/_ilm/policy/scouter-2.7.0?pretty" -H 'Content-Type: application/json' -d@config/lifecycle-policy.json
 _menu
}

function _mapping(){
 echo "Index mapping template setting"
 curl -u ${ES_USER}:${ES_PASSWORD} -v -XPUT "$ES_ADDRESS:9200/_template/scouter-2.7.0?pretty" -H 'Content-Type: application/json' -d@config/index-setting-mappings.json
 _menu
}

function _import(){
 echo "Scouter object setting"
 curl -u ${ES_USER}:${ES_PASSWORD} -v -XPOST "$ES_ADDRESS:5601/api/saved_objects/_import" -H 'kbn-xsrf: true' --form file=@dashboard/scouter-dahsboard.ndjson
 _menu
}
function _count() {
  curl -u ${ES_USER}:${ES_PASSWORD} -v GET "$ES_ADDRESS:9200/scouter-2.7.0*/_count?pretty"
  _menu
}
function _menu(){
    echo "======================================================================="
    echo " 1. Scouter Index Life Cycle Running"
    echo " 2. Scouter Index mapping template Running"
    echo " 3. Scouter Dashboard object import Running"
    echo " 4. Scouter Index Count Check"
    echo "======================================================================="
}
_menu
while read choice
do
     case $choice in
           1) _ilm
                ;;
           2) _mapping
                ;;
           3) _import
                ;;
           4) _count
                ;;
           *) _menu
                ;;
     esac
done