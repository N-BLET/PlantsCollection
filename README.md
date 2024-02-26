# PlantsCollection

## Description
Ce dépôt GitHub correspond au développement d’une application mobile développée en Kotlin avec l'IDE Android Studio. L'objectif de cette application est de permettre aux utilisateurs de partager des informations sur des plantes et de gérer leur propre collection de plantes favorites.

## Structure de l'Application
L'application est constituée de 3 activités, 3 fragments et une popup.

### Activités
1. Page de Connexion
2. Page de Création de Compte Utilisateur
3. Page Principale

### Fragments
1. Accueil
2. Collection Personnalisée
3. Compte Utilisateur

### Popup
Une popup est utilisée pour ajouter de nouvelles plantes à la base de données.

## Isolation de l'Application
Le choix de 3 activités vise à bien isoler l'application de la connexion et de la création de compte.

## Fonctionnalités des Fragments
1. **Accueil :** Ce fragment affiche deux RecyclerViews. Un en mode horizontal pour afficher les plantes non sélectionnées par l'utilisateur et l'autre en mode vertical pour afficher toutes les plantes de la base de données Firebase.
2. **Collection Personnalisée :** Ce fragment affiche un RecyclerView vertical répertoriant toutes les plantes sélectionnées par l'utilisateur (liked).
3. **Compte Utilisateur :** Ce fragment permet à l'utilisateur de modifier ses informations de compte.

## Technologies Utilisées
- Kotlin
- Android Studio
- Firebase (pour la base de données)
