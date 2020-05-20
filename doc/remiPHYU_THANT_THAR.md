# Fiche personnelle d'activité:

## Résumé de l'activité
Pour ce projet de **Internet des objets**, notre binôme a tout d'abord travaillé ensemble sur la conception de détecteur de pollution. 

Dans un premier temps, je me suis chargé de l'étude de marché pour les differentes stations de détecteur de pollution connectées en générale, d'usage à l'exterieur et l'interieur. En discutant sur les possibilité de réalisation et nos moyens, nous avons ensuite décidé de nous pencher sur un détecteur de pollution maison, qui peut aussi se servir de station de météo, permettant de détecter differents polluants présents à l'interieur des habitations. Suite à cet accord, nous avons pu nous décider sur les composants nécessaires et essentiels à utiliser pour la station. 

Lors de la [récuperation des données par les capteurs](https://github.com/institut-galilee/2020-captain-planet/blob/master/src/Projet_Code.ino), j'ai pu assisté Heytem, notamment lorsqu'il  y a eu des problèmes rencontrés avec les erreurs de mauvaises valeurs capturées. 
 
Après avoir réfléchi sur la manière de récuperer les données détectées, nous nous sommes mis d'accord sur l'utilisation de **ThingSpeak**. Je me suis ensuite chargé de la conception de l'application mobile du projet, en utilisant **Android Studio**, en réfléchissant sur la base de presentation et les fonctionnalités requises de l'application. Les fonctionnalités réalisées sont:
- [Page Principale](https://github.com/institut-galilee/2020-captain-planet/blob/master/src/AndroidProject/app/src/main/java/com/example/captainplanet/MainActivity.java), qui récupere les fichiers de données captées en json, fourni par notre channel de **Thingspeak**, et affiche les informations de données capturées par la station, en s'inspirant des tutoriels disponsibles.
 - [Page de graphes de données](https://github.com/institut-galilee/2020-captain-planet/blob/master/src/AndroidProject/app/src/main/java/com/example/captainplanet/Main2Activity.java), qui se charge d'afficher l'évolution des données captées sous forme de graphe.

Enfin, j'ai travaillé avec Heytem pour le rédaction du [rapport](https://github.com/institut-galilee/2020-captain-planet/blob/master/doc/report.pdf) et la video de présentation.


## Les connaissances acquises
Ce projet m'a tout d'abord fait découvrir le monde de l'internet des objets qui m'étais assez inconnu, avec toutes les possibilités des conceptions et d'utilisation des objets connectés dans nombreux domaines différents. J'ai pu donc apprendre les notions de base et fonctionnalités des differents composantes avec le kit Arduino, et la programmation avec **Arduino IDE**.
Dans un deuxième temps, j'ai pu apprendre la conception d'une application mobile sur **Android Studio** grâce à ce projet. Pour mes premiers pas, j'ai appris les notions de base de Android Studio en dehors de l'application réalisée pour le projet avec les tutoriels et les cours disponsibles, en passant par la creation des pages, les éléments d'interface utilisateur, et la programmation.
