


# Hôpital Intelligent - Gestion des Capteurs Connectés

Ce projet simule un **système d’hôpital intelligent** capable de gérer différents capteurs médicaux connectés, suivre les abonnements associés et générer automatiquement des alertes en cas de valeurs anormales.

## Fonctionnalités principales

* **Gestion des capteurs** :
  Ajout, suppression, modification et mesure de différents capteurs :

  * modele.Pilulier (gestion des doses médicamenteuses)
  * Tensiomètre (tension artérielle)
  * Oxymètre (saturation en oxygène)
  * Holter ECG (fréquence cardiaque)
  * Glucomètre (glycémie)
  * modele.Balance (poids)

* **Alertes automatiques** :
  Chaque capteur déclenche une alerte si ses valeurs dépassent un seuil critique. Les alertes sont historisées et peuvent être marquées comme traitées.

* **Gestion des abonnements** :
  Chaque capteur peut être lié à un abonnement (mensuel ou annuel), avec possibilité de renouveler ou désactiver un abonnement.

* **Export et sauvegarde** :

  * Sauvegarde des données de capteurs et d’alertes sur fichiers (`.dat`)
  * Export au format CSV pour une consultation ou analyse externe

* **Interface console** :
  Menu interactif pour gérer facilement les capteurs, abonnements et alertes.

## Objectif du projet

Ce projet a pour but de simuler un environnement hospitalier connecté, permettant de **surveiller en temps réel l’état de santé des patients** via des capteurs, tout en offrant une **gestion simplifiée des alertes et abonnements**.


#  **Description des classes du projet**

##  **1. modele.CapteurConnecte (classe abstraite parente)**

C’est la classe **mère** de tous les capteurs.
Elle contient les éléments communs :

### Attributs communs :

* `id` → identifiant unique du capteur
* `nom` → nom du capteur
* `valeur` → dernière mesure
* `estAbonne` → indique si l’utilisateur reçoit des alertes
* `abonnement` → objet modele.Abonnement lié au capteur

### Méthodes :

* `mesurer()` → sera redéfinie par chaque capteur
* `verifierAlerte()` → vérifie si une alerte doit être déclenchée
* `setAbonnement()`, `estAbonne()`, `getId()`, `getNom()`, `getValeur()`

 **But :** Servir de modèle commun pour tous les capteurs.

---

## **2. modele.Pilulier**

Simule un pilulier qui suit le nombre de doses restantes.

### Fonctionnement :

* Commence avec un nombre de doses initiales.
* À chaque mesure : **30% de chance qu’une dose soit consommée**.
* Déclenche une alerte quand `dosesRestantes == 0`.

 **Utilité :** Prévenir lorsque le patient n’a plus de médicament.

---

## **3. modele.Tensiometre**

Simule une mesure de tension artérielle.

### Génération :

* Systolique : entre **90 et 160**
* Diastolique : entre **50 et 100**

### Conditions d’alerte :

* Systolique > 140
* ou Diastolique > 90
  → **Hypertension**

 **Utilité :** Détecter des tensions dangereusement élevées.

---

##  **4. modele.Oxymetre**

Mesure artificiellement la saturation en oxygène (SpO2).

### Génération :

* Valeur entre **88% et 100%**

### Condition d’alerte :

* SpO2 < 92 %
  → **Désaturation**

 **Utilité :** Surveiller l’oxygénation d’un patient.

---

##  **5. modele.Holter_ECG**

Simule des battements cardiaques.

### Génération :

* Fréquence cardiaque entre **40 et 150 bpm**

### Alertes :

* < 50 bpm → **Bradycardie**
* > 120 bpm → **Tachycardie**

 **Utilité :** Détecter des rythmes cardiaques anormaux.

---

## **6. modele.Glucometre**

Simule une mesure de glycémie.

### Génération :

* Glycémie entre **0.60 g/L et 2.20 g/L**

### Conditions d’alerte :

* < 0.70 g/L → Hypoglycémie
* > 1.80 g/L → Hyperglycémie

 **Utilité :** Surveiller les risques liés au diabète.

---

##  **7. modele.Abonnement**

Représente un abonnement (mensuel ou annuel) associé à un capteur.

### Attributs :

* `type` → Mensuel ou Annuel
* `dateDebut`
* `dateFin`
* Vérification que la date n’est pas dans le passé

 **Utilité :** Permet d’activer les alertes et le suivi du capteur.

---

##  **8. modele.Hopital**

Gère **tous les capteurs** installés.

### Responsabilités :

* Ajouter ou supprimer un capteur
* Mesurer tous les capteurs
* Afficher les alertes actives
* Sauvegarder/charger les capteurs (fichier `.dat`)
* Exporter en CSV
* Afficher la liste des capteurs
* Statistiques (abonnés, alertes, etc.)

 **Utilité :** C’est le **système central** de gestion des capteurs.

---

##  **9. modele.GestionCapteurs**

Gère toutes les **interactions utilisateur** via un menu.

### Permet :

* Ajouter un capteur
* Supprimer
* Modifier
* Filtrer par type
* Rechercher
* Afficher les non abonnés
* Mesurer tous les capteurs
* Afficher statistiques

 **Utilité :** Interface console pour manipuler les capteurs.

---

##  **10. modele.GestionAlarmes**

Gère l’historique des alarmes.

### Fonctionnalités :

* Créer une alarme si un capteur dépasse les seuils
* Afficher toutes les alarmes
* Filtrer les alarmes non traitées
* Marquer une alarme comme traitée
* Exporter en CSV

 **Utilité :** Assure le suivi des problèmes détectés par les capteurs.


