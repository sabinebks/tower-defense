# Tower Defense

Jeu de défense de tours en console : placer et faire évoluer des tours pour arrêter des vagues de ballons avant leur sortie du plateau.

**Projet universitaire réalisé en équipe à l’Université de Lille**, dans le cadre de la L2 informatique, semestre 4, en 2026.

## Fonctionnalités présentes

- Deux types de plateaux : chemin généré et chemins rectilignes.
- Tours spécialisées, projectiles, effets de ralentissement et de gel.
- Achat, vente, amélioration et retrait d’évolutions des tours.
- Gestion des manches, des crédits et des vies du joueur.
- Choix d’un joueur humain ou automatique ; affichage textuel du plateau et des événements.

## Technologies

Java 17+ · programmation orientée objet · JUnit · Make · GitLab CI.

## Installation et lancement

Prérequis : **JDK 17+** et, pour les commandes du Makefile, un environnement avec **GNU Make et un shell Unix** (Linux, macOS ou WSL).

```bash
git clone https://github.com/sabinebks/tower-defense.git
cd tower-defense
make classes
java -cp classes Livrables.Livrable6 11 11 15 chemin humain
```

Les cinq arguments sont : hauteur, largeur, nombre de ballons (**au moins 15**), type de plateau (`chemin` ou `libre`) et type de joueur (`humain` ou `aleatoire`).

Sous PowerShell, la compilation peut aussi se faire sans Make :

```powershell
$sources = Get-ChildItem src -Recurse -Filter *.java | ForEach-Object FullName
javac -encoding UTF-8 -d classes $sources
java -cp classes Livrables.Livrable6 11 11 15 chemin humain
```

`make jar` construit les archives exécutables des quatre lanceurs dans `jar/`. Par exemple :

```bash
java -jar jar/towerdefense-a-interactive.jar 11 11
```

### Tests

Les tests nécessitent le lanceur autonome JUnit nommé `junit-console.jar`, fourni avec le projet. Sous PowerShell, après compilation des sources :

```powershell
$tests = Get-ChildItem test -Recurse -Filter *.java | ForEach-Object FullName
javac -encoding UTF-8 -cp "junit-console.jar;classes" -d classes $tests
java -jar junit-console.jar -cp classes --scan-class-path
```

Sous Linux/macOS, utiliser `:` à la place de `;`. Le Makefile propose aussi `make runtests` ; sa liste de compilation des tests n’inclut pas le sous-dossier `test/entity/tower/evolution`, que la commande récursive ci-dessus couvre.

La compilation des sources et les **79 tests existants** ont réussi avec le JDK 25.0.2 lors de la préparation du portfolio. Cela ne constitue pas une validation exhaustive des parties interactives.

## Équipe et contributions

- **Son Tung DO**
- **Sabine Boukais**
- **Ines Agueniou**
- **Yanni Moutchou**

L’historique Git est conservé avec ses auteurs et contributeurs. Les commits de Sabine montrent notamment des interventions sur les plateaux, la gestion des tours et des actions, les manches, l’horloge, la boucle de jeu, les tests et la documentation. Ces contributions s’inscrivent dans un travail collectif.

Le projet provient du dépôt GitLab universitaire de l’équipe. Aucune licence de redistribution du code du projet n’est présente ; aucune licence n’a été ajoutée arbitrairement. Le lanceur JUnit conserve ses propres mentions et licences.

## Documentation

- [Compte rendu des livrables et diagramme UML](docs/README-livrables.md) : documentation historique, dont certaines anciennes commandes ne correspondent plus à l’arborescence actuelle.
- [Diagramme du projet](diagramme.png).
- [Présentation de l’équipe](diapo/diapo_groupe5_compressed.pdf).

Pour lancer la version actuelle, utiliser les commandes de ce README.
