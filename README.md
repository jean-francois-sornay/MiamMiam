# Projet mobile MiamMiam
# ====================

# But du projet
Le but de ce projet est de créer une application mobile permettant de consommer l'API [TheMealDB](https://themealdb.com/)
en utilisant le langage de programmation Kotlin. L'application doit permettre de consulter les recettes de cuisine, de
consulter les détails d'une recette tout cela en passant d'une vue des différentes catégorie à une vue des recettes
disponible pour cette catégorie pour arriver aux détails de la recette choisie.

# Gestions de projet

## Organisation du travail
Comme vous pouvez le remarquer en analysant les commits de ce projet, nous avons mis longtemps à commencer
à réellement travailler sur le projet. Cela est dû à deux choses :
- Nous sommes également en binôme sur d'autres projets et avons pris la décision de ne pas commencer à coder
  avant d'avoir fini certains autres projets afin de ne pas nous disperser. Cela ne veut pas dire que nous n'avons
  travaillé sur le projet en parallèle de ces autres projets. En effet, nous avons mis au clair le cahier des charges
  et la structure de l'application afin de préparer et d'avoir un développement rapide.
- Nous avons eu du mal à nous mettre d'accord sur le design et les fonctionnalités prévus de l'application. Nous avons donc décidé de commencer
  par le design avant de commencer à coder.

Un deuxième point que l'on peut potentiellement voir est le fait que nous avons choisi de travailler en pair
programming. Nous avons choisi cette méthode car nous avons trouvé que cela permettait de mieux partager les
connaissances et de mieux comprendre le code de l'autre. Nous avons également choisi cette méthode car nous
avons trouvé que cela permettait de mieux gérer le temps de travail et de mieux répartir les tâches surtout 
qu'elles étaient assez simples. De plus, pour un si petit projet, nous avons trouvé que cela permettait de
travailler plus efficacement et de pouvoir avoir et comprendre plus rapidement les problématiques et enjeux de la conception mobile.

## Détermination des tâches
Les tâches ont été déterminées et renseignées durant les premières séances en fonction des fonctionnalités de l'application. Chaque fonctionnalité a été découpée en 
plusieurs tâches plus petites. Cette découpe a été faite en fonction de la complexité de la tâche et de la disponibilité
des membres du groupe pour travailler sur le projet dans un commun accord.

Pour mieux nous aider à visualiser le travail à faire, nous avons dans un premier temps fait des maquettes de l'application
afin d'avoir un rendu visuel des différents écrans de l'application. Cela nous a permis de mieux nous organiser et de
savoir ce que nous devions faire.

## Gestion de version
Nous avons utilisé le logiciel Git pour gérer les différentes versions de notre projet. Nous avons créé un dépôt sur
GitHub et nous avons travaillé sur des branches différentes pour chaque fonctionnalité.
Nous avons utilisé le même principe que GitFlow pour gérer les branches. Nous avons donc une branche `master` qui
contient la version stable du projet et des branches `feature` qui contiennent les différentes fonctionnalités de l'application.
Nous avons également une branche `develop` qui contient la version en cours de développement.


# Fonctionnalités implémentées

## Afficher la liste des catégories de recettes
Pour la liste des catégories de recettes, nous avons choisi d'avoir un design simple et épuré. Nous avons donc choisi
de n'afficher que le nom de la catégorie et une image de la catégorie. Nous y avons ajouté une courte description
de la catégorie afin de donner un peu plus d'informations à l'utilisateur mais surtout de tester et montrer
que nous avions compris le principe de structuration d'éléments de design (cardview, textview, imageview, etc.).

## Afficher la liste des recettes d'une catégorie
Nous avons choisi d'afficher la liste des recettes d'une catégorie sur la même idée que la liste des catégories.
Nous avons donc choisi d'afficher le nom de la recette et une image de la recette mais cette fois-ci nous avons
choisi d'avoir un layout de type grid afin de pouvoir afficher plus d'éléments sur la même page et de mettre en avant
les images des recettes.

## Afficher les détails d'une recette
Les points importants à afficher dans cet écran sont :
 * Le nom de la recette
 * Une image de la recette
 * La liste des ingrédients d'une recette
 * La liste des étapes d'une recette
 * Le lien vers la vidéo de la recette

Nous avons donc choisi d'afficher ces informations dans un layout de type scrollview afin de pouvoir afficher
toutes les informations de la recette. Nous avons également choisi d'afficher les ingrédients et les étapes dans
des listes afin de pouvoir les afficher de manière plus lisible.

## Afficher une recette aléatoire

 * Depuis l'écran d'accueil (liste des catégories)
Pour implémenter cette fonctionnalité, nous avons utilisé le endpoint `www.themealdb.com/api/json/v1/1/random.php` de l'API TheMealDB. 
Celui-ci permet de récupérer une recette aléatoire. Nous avons donc utilisé un booléen "isRandom" lors de la transmission
de l'activité de l'écran d'accueil à l'écran de détails de la recette afin de savoir si l'on doit utiliser cette endpoint 
à la place de l'endpoint `www.themealdb.com/api/json/v1/1/lookup.php?i=ID` qui permet de récupérer une recette en fonction de son ID.

 * Depuis l'écran d'une catégorie (liste des recettes d'une catégorie)
Pour implémenter cette fonctionnalité, nous utilisons simplement les recettes déjà récupérées lors de l'affichage de la liste des recettes d'une catégorie.
Nous récupérons donc une recette aléatoire dans cette liste puis nous en transmettons l'ID à l'activité de détails de la recette afin de récupérer
les détails de cette recette.


# Difficultés rencontrées

 * La gestion des appels API (difficulté simple à résoudre)
Nous avons eu quelques difficultés à gérer les appels API. En effet, nous avons eu du mal à comprendre comment
les appels API fonctionnaient et comment nous pouvions les utiliser. Nous avons donc dû nous documenter sur le sujet
et nous avons trouvé des tutoriels qui nous ont aidés à comprendre comment les appels API fonctionnaient et comment
nous pouvions les utiliser dans notre application. Par exemple la gestion d'erreurs et d'élément manquant dans les appels API.

 * La gestion des images
Ici nous sommes plus sur une difficulté se rapportant au design de l'application. En effet, nous avons parfois eu du mal à
mettre en place les images dans l'application. Nous avons parfois eu des problèmes de taille d'image ou de positionnement
d'image.

 * La gestion des RecyclerView
Nous avons eu quelques difficultés à gérer les RecyclerView. En effet, nous avons eu du mal à comprendre comment les utiliser
et comment nous pouvions les adapter à notre application. Nous avons donc dû nous documenter sur le sujet et nous avons trouvé
des tutoriels qui nous ont aidés à comprendre comment les RecyclerView fonctionnaient et comment nous pouvions les utiliser dans
notre application.

 * Le design de l'application
Nous avons eu quelques difficultés à mettre en place le design de l'application. En effet, nous avons parfois eu du mal à trouver
des images pour certains éléments de l'application. Nous avons également eu quelques difficultés à trouver le bon équilibre entre
le design et le fonctionnel de l'application.

 * La gestion du temps 

Il s'agit d'une difficulté externe à notre projet. En effet, ayant plusieurs cours et projets en parallèle, nous avons eu le regret de ne pas pouvoir
travailler plus sur notre projet. Nous avons consacré beaucoup de temps à nos autres projets et nous n'avons pas pu travailler autant que nous le
voulions sur notre projet. Un imposant nouveau projet a démarré mi-décembre ce qui a chamboulé notre organisation
, surtout que l'un d'entre nous travaillait pendant les deux semaines de fêtes et ne pouvait donc pas être présent pour faire du pair programming.
Nous nous sommes retrouvés bloqué par le temps à développer au point 90% de l'application en une semaine là ou nous en avions prévu deux.
C'est l'un des points qui nous a le plus frustré dans ce projet car il était très intéressant et nous aurions aimé pouvoir le développer plus en profondeur.

Les fonctionnalités suivantes que nous pensions ajouter au projet n'ont donc pas pu être implémentées :
 * Pouvoir rechercher une recette par son nom
 * Ajout d'informations supplémentaires sur la recette (ex : origine, tags, etc.)
 * Pouvoir filtrer les recettes par des tags ou des origines
 * Pouvoir filtrer les recettes par des ingrédients
 * Gestion d'une liste de recettes favorites


# Apréciations sur le projet

Ce projet nous a permis de découvrir le langage Kotlin et de nous donner un bon aperçu de certaines problématiques de développement mobile
comme par exemple la gestion d'API. Nous avons trouvé qu'il permettait vraiment de mieux se familiariser avec le développement mobile
et de mieux comprendre les enjeux de la conception mobile. Nous avons également trouvé que ce projet était très intéressant car il permettait
de découvrir un nouveau langage de programmation et de découvrir une nouvelle façon de développer.


# Auteurs
# -------
# - [Jean-François SORNAY](https://github.com/jean-francois-sornay/)
# - [Thomas ALEXALINE](https://github.com/Megacruxis)
