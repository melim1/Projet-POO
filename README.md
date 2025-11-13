
# ProjetPOO

 on a **une classe parente abstraite** appelée `CapteurConnecte`. Cette classe définit les **attributs communs** à tous les capteurs, comme :

* `id` et `nom` pour identifier le capteur,
* `estAbonne` pour savoir si l’utilisateur est abonné au suivi du capteur,
* `valeur` qui stocke la dernière mesure.

Elle définit aussi des **méthodes communes** :

* pour récupérer les informations (`getId()`, `getNom()`, `getValeur()`, etc.),
* et deux méthodes abstraites `mesurer()` et `verifierAlerte()` que chaque capteur devra **implémenter spécifiquement**.

Ensuite, il y a **des classes enfants** qui héritent de `CapteurConnecte` :

1. **Tensiometre** : mesure la tension systolique et diastolique, avec une alerte si la tension est trop haute.
2. **Oxymetre** : mesure la saturation en oxygène, avec alerte si SpO2 < 92%.
3. **Balance** : mesure le poids, avec alerte si le poids est trop bas ou trop élevé.
4. **Pilulier** : suit le nombre de doses restantes, avec alerte si  plus de doses.

Chaque classe enfant **implémente ses propres méthodes `mesurer()` et `verifierAlerte()`** selon le type de capteur, mais hérite des fonctionnalités communes de la classe parente.