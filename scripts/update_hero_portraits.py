import json
import os
import urllib2

DEBUG = True

URL_HEROES_JSON = 'http://heroesjson.com/heroes.json'
PATH_HEROES_JSON = 'json/heroes.json'
URL_HERO_PORTRAIT_ICON = 'http://www.heroesdata.ru/icons/hero/{hero_name}.png'
PATH_HEROES_ICON = os.path.join('icons', 'hero', '{}.png')

# Known errors when downloading heroes by name:
# Anub'arak, Butcher, E.T.C., Kael'thas, Li Li, Li-Ming, The Lost Vikings, Lt. Morales, Rehgar, Rexxar, Sgt. Hammer
HERO_NAME_PORTRAIT_MAP = {
    "Anub'arak": "Anubarak",
    "Butcher": "The_Butcher",
    "E.T.C.": "Elite_Tauren_Chieftain",
    "Kael'thas": "Kaelthas",
    "Li Li": "Li_Li",
    "Li-Ming": "Li_Ming",
    "The Lost Vikings": "The_Lost_Vikings",
    "Lt. Morales": "Morales",
    "Rehgar": "Reghar",
    "Rexxar": "Rexar",
    "Sgt. Hammer": "Sergeant_Hammer"
}


def _read_heroes_json():
    if DEBUG:
        with open(PATH_HEROES_JSON, 'r') as fp:
            json.load(fp)
    response = urllib2.urlopen(URL_HEROES_JSON)
    json_raw = response.read()
    with open(PATH_HEROES_JSON, 'wb') as fp:
        fp.write(json_raw)
    return json.loads(json_raw)


def _download_hero_portrait_icon(hero_name):
    if hero_name is None:
        return
    hero_name = _normalize_hero_name(hero_name)
    img = urllib2\
        .urlopen(URL_HERO_PORTRAIT_ICON.format(hero_name=hero_name))\
        .read()
    with open(PATH_HEROES_ICON.format(hero_name.lower()), 'wb') as fp:
        fp.write(img)


def _normalize_hero_name(name):
    if name in HERO_NAME_PORTRAIT_MAP:
        name = HERO_NAME_PORTRAIT_MAP.get(name, name)
    return name.lower()


def update_portraits():
    data = _read_heroes_json()
    for hero in data:
        # for k in hero.iterkeys():
        #     print "%s: %s" % (k, hero.get(k))
        hero_name = hero.get('name')
        try:
            _download_hero_portrait_icon(hero_name)
            print "OK {} portrait".format(hero_name)
        except Exception as e:
            print "Error {} portrait: {}".format(hero_name, e.message)
    print "Download finished"

if __name__ == "__main__":
    update_portraits()
