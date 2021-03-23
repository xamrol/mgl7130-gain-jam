# mgl7130-gain-jam
# Introduction
Ce projet est proposé par l'équipe Gain, qui est composée de Arol Gbeto-Fia et Larbi Ait Djebara.
Nous avons choisi de travailler sur une aplication de gestion d'emplois (JobAroundMe ou JAM).
Nous identifions principalement trois (03) fonctionnalités à savoir :
- La gestion des profils utilisateurs (Création, Modification, Ouverture de session)
- La consultation des emplois disponibles
- Les candidatures des utilisateurs pour les offres proposées

# Point d'étape
- A ce jour, nous avons entièrement développé la gestion des profils utilisateurs, en fournissant des interfaces d'ouverture de session, de réinitialisation de mot de passe et de création d'un nouveau profil.
- Nous avons adopté l'architecture MVC pour le développement et tout le long de nos travaux
- Nous avons utilisé une base de données SQLite qui sert de tremplin pour le démarrage
- Nous avons aussi utilisé la bibliothèque Room dans Android Studio pour l'interfaçage de la base de données
- Nous nous sommes aussi servis des SharedPreferences et des Bundle pour de la persistence d'informations

# Données disponibles
Afin de procéder aux tests, nous avons créé dans la base de données, quelques profils déjà exploitables identifiés comme suit :
- User : test0 ; Password : test0
- User : test1 ; Password : test1
- User : test2 ; Password : test2

# Procédure de vérification
1. Exécuter le code
2. Sur l'interface d'accueil, cliquer sur le bouton Inscription pour créer un nouveau profil ou sur le bouton Se connecter pour ouvrir une session
3. Sur l'interface de connexion, nous avons prévu deux liens permettant d'accéder respectivement aux interfaces de réinitialisation de mot de passe et de création de nouveau profil. Les champs des formulaires sont soumis à des vérifications diverses.
4. Lorsqu'un utilisateur arrive à s'authentifier, nous lui présentons la liste des emplois dipsonibles (en cours de développement)

# Fonctionnalités restantes
Pour la deuxième partie, nous devons travailler sur :
- La liste des emplois avec une RecyclerView
- L'affichage des emplois en mode Carte de géolocalisation
- La gestion des candidatures
- L'amélioration de l'espace de travail des utilisateurs
