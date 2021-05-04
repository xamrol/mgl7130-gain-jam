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
- Nous nous sommes aussi servis des SharedPreferences et des Extras pour de la persistence d'informations
- L'implémentation de la carte des emplois, de la liste des emplois et de la gestion des profils est complète
- Nous avons également écrit des tests unitaires (principalement dans le fichier source AppDatabaseTest.java) pour nous assurer que l'implémentation est correcte

# Données disponibles
Afin de procéder aux tests, nous avons créé dans la base de données, quelques profils déjà exploitables identifiés comme suit :
- User : test0 ; Password : test0
- User : test1 ; Password : test1
- User : test2 ; Password : test2

# Procédure de vérification
Remarque : Pour une première exécution du code, il faut s'assuer de décommenter la ligne numréo 72 (.createFromasset...) dans le fichier src/mainéjava/com/example/jam/LoginActivity.java pour que la création de la base de données Room se fasse. Ensuite, on peut la commenter à nouveau pour tous les autres essais.
1. Exécuter le code
2. Sur l'interface d'accueil, cliquer sur le bouton Inscription pour créer un nouveau profil ou sur le bouton Se connecter pour ouvrir une session
3. Sur l'interface de connexion, nous avons prévu deux liens permettant d'accéder respectivement aux interfaces de réinitialisation de mot de passe et de création de nouveau profil. Les champs des formulaires sont soumis à des vérifications diverses.
4. Lorsqu'un utilisateur arrive à s'authentifier, nous lui présentons la liste des emplois disponibles
5. Il est possible d'avoir des détails de chaque offre en cliquant dessus et de postuler par la suite
6. Une barre de navigation (menus) est créée et permet d'accéder au profil, à la carte des emplois et à la liste des emplois
7. Les marqueurs présents sur la carte des emplois est cliquable et permet d'accéder aux détails de l'emploi choisi
8. Il est possible de mettre à jour les données de l'utilisateur (nom, prénom, mot de passe) et de fermer la session courante à partir de l'interface présentant le profil

# Améliorations futures
Pour des améliorations futures, nous pensons à :
- Permettre l'authentification à l'application à partir de comptes Facebook et Google
- Utiliser une base de données centralisée (externe) et adopter une architecture REST pour le traitement des requêtes
