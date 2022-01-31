# Messaging

## Introduction

Messaging est une implémentation servant d'exemple pour la création d'un système de messagerie. Ce dépôt contient tout ce dont vous avez besoin pour commencer à implémenter ce système. Plusieurs dossiers sont présent afin de mettre en place les différentes parties du système: database, backend, frontend, java-api, docker. L'utilité de ces dossiers sera présenté ci-dessous.

## Table des matières

 - [Introduction](#Introduction)
 - [Table des matières](#Table-des-matières)
 - [Mise en place](#Mise-en-place)
 - [Construction](#Construction)
	 - [Frontend web](#Frontend-web)
	 - [Java](#Java)
 - [Utilisation](#Utilisation)

## Mise en place

Quelques étapes sont nécessaires afin de mettre en place ce système. Voici les étapes principales:

 1. Mise en place de la base de données
 2. Mise en place de l'API backend
 3. Configuration de l'API

### Mise en place de la base de données

Tout d'abord, vous aurez besoin d'une instance de base de données MySQL. Je vous recommande d'installer un serveur MySQL en local, qui sera beaucoup plus facile à accéder et à configurer. Vous pouvez utiliser ces logiciels selon votre OS:

 - Windows: WAMP. C'est un logiciel incluant Apache, MySQL et PHP. Seul le serveur MySQL nous intéressera
 - MacOS: XAMP. C'est l'équivalent de WAMP pour OSX
 - Linux: Téléchargez le serveur MySQL avec le gestionnaire de paquet fourni par votre distribution. Par exemple, pour ubuntu:

> sudo apt-get install -y mysql-server

Créez ensuite un utilisateur pour la base de données. Vous pouvez utiliser PhpMyAdmin fourni par WAMP et XAMP. Pour Linux, référez-vous à la [documentation](https://dev.mysql.com/doc/).
Créez ensuite une base de données qui nous servira pour Messaging. Nommez-là comme bon vous semble. Assurez-vous que votre utilisateur précédemment créé à toutes les permissions sur cette base.

Enfin, importez le fichier .sql contenu dans le dossier "database" dans votre base de données. Cela peut également être effectué via PhpMyAdmin.

### Mise en place de l'API backend

Pour mettre en place l'API, vous devrez installer Node.JS. Téléchargez-le [ici](https://nodejs.org/en/). Laissez toutes les options par défaut. Une fois Node.JS installé, nous allons avoir besoin de l'invite de commande. Ouvrez-là (pour Windows, utilisez Windows Terminal, Powershell ou CMD).
Rendez-vous dans le dossier "backend" avec votre terminal (tip: pour windows, pous pouvez directement vous placer dans le dossier en l'ouvrant dans l'explorateur, puis Alt+Clic droit, puis Terminal).

Une fois que vous êtes dans le dossier, il faudra installer les dépendances. Exécutez juste:

> npm install

Cette commande installera automatiquement toutes les dépendances.

### Configuration de l'API

Il va ensuite falloir faire le lien entre notre API et la base de données MySQL. Pour cela, ouvrez le fichier ".env.defaults" avec votre éditeur de texte, puis changez les valeurs selon vos propres besoins. Enregistrez ensuite le fichier sous le nom ".env"

Pour lancer l'api, il suffit d'exécuter la commande suivante dans le terminal ouvert précédemment:

> npm start

Cette commande va lancer l'API en local, sur le port 8081.

## Construction

Pour pouvoir utiliser cette implémentation et l'API, nous allons avoir besoin de clients. Vous trouverez dans ce dépôt 2 clients: un en Web, avec le framework Vue.JS, et une en Java.

### Frontend web

#### Configuration
---
Le frontend est dévloppé avec Node.JS et le framework Vue.JS. Tout comme le backend, nous allons d'abord avoir besoin de télécharger les dépendances. Ouvrez un terminal dans le dossier "frontend", puis lancez:

> npm install

L'installation peut prendre quelques secondes à quelques minutes. Une fois les dépendances installées, nous allons devoir configurer le frontend pour communiquer avec l'API. Si vous avez installé l'API en local, vous pouvez  directement passer au déploiement.
Si vous avez installé l'API sur une autre machine, il faudra alors éditer le fichier "frontend/src/api/index.js" et changer la variable "BASE_URL" afin d'y placer l'adresse IP et le port où tourne votre API.

#### Déploiement
---
Pour lancer le frontend en local, il suffit de taper la commande:

> npm run serve

Cette commande va compiler le JavaScript et lancer un serveur en local.
Vous pouvez maintenant accéder à l'interface depuis votre navigateur sur l'URL:

> http://localhost:8080

Si tout c'est bien passé, vous devriez pouvoir accéder à l'interface et utiliser Messaging. Sinon, revérifiez votre installation

### Java


