# l2s4-projet-2026

## Equipe 5
- Son Tung DO
- Sabine Boukais
- Ines Agueniou
- Yanni Moutchou

---

## Sujet
[Le sujet 2026](lien-vers-sujet)

---

## Livrables

### Livrable 1

#### Atteinte des objectifs

Le projet remplit actuellement les objectifs du premier livrable, à savoir la mise en place d'un système de plateau capable de générer deux types de structures de jeu distinctes. Deux types de plateaux ont été créés : le `FreeBoard`, où les chemins sont des lignes droites allant d'un bord à l'autre, et le `DefinedBoard`, où un chemin est généré de manière aléatoire en utilisant une logique de voisins pour éviter les boucles et faire en sorte que chaque cellule ne soit utilisée qu'une seule fois.

Chaque cellule est représentée par un objet `Cell`, qui contient sa position et indique si elle fait partie du chemin. Le plateau est représenté par une classe abstraite `Board`, qui contient la grille de cellules ainsi que des méthodes communes pour accéder aux cellules, vérifier les limites et manipuler les chemins. Cette abstraction permet de centraliser les fonctionnalités partagées et de faciliter l'extension : de nouveaux types de plateaux peuvent être créés en héritant de `Board` et en implémentant leurs propres règles de génération de chemins.

Pour `FreeBoard`, un algorithme simple parcourt les bords du plateau pour créer des chemins droits, en s'assurant que le départ et l'arrivée sont toujours sur un bord. Les positions déjà utilisées et leurs voisins sont bloqués pour éviter que les chemins se chevauchent.

Pour `DefinedBoard`, un algorithme aléatoire génère un chemin "serpent" : à chaque étape, la cellule suivante est choisie parmi les voisins disponibles, en évitant de revenir en arrière ou de dépasser les bords. Une longueur minimale est imposée pour éviter que le chemin ne se termine trop vite. Cela garantit un chemin unique et continu sur le plateau.

Difficultés rencontrées : il a été compliqué de trouver le code exact pour générer correctement le chemin aléatoire. Au début, des bugs apparaissaient souvent, comme des chemins très courts allant de deux cases seulement (départ et fin), ou des chemins qui se terminaient trop vite. Il a fallu ajuster l'algorithme et ajouter des vérifications pour garantir une longueur minimale et un chemin continu sans chevauchement.

#### Commandes

```bash
# Compiler toutes les classes
javac -d classes src/**/*.java

# Exécuter DefinedBoardMain (Livrable1a)
java -cp classes board.Livrable1a 11 5

# Exécuter FreeBoardMain (Livrable1b)
java -cp classes board.Livrable1b 11 5 1

# Compilation des tests
javac -cp junit-console.jar:classes -d classes test/board/*.java

# Exécuter les tests
java -jar junit-console.jar -cp classes --scan-class-path

# Créer le JAR (Livrable1a)
jar cvfe livrable1a.jar board.Livrable1a -C classes .
java -jar livrable1a.jar

# Créer le JAR (Livrable1b)
jar cvfe livrable1b.jar board.Livrable1b -C classes .
java -jar livrable1b.jar
```

#### Difficultés restant à résoudre
Pour le moment, toutes les fonctionnalités du livrable 1 fonctionnent correctement.

---

### Livrable 2

#### Atteinte des objectifs

Le projet remplit actuellement les objectifs du deuxième livrable, à savoir la création des ballons et la gestion de leur progression sur le plateau en fonction du temps.

Les ballons sont représentés par une hiérarchie de classes utilisant l'héritage : une classe mère `Balloon` et deux sous-classes `BalloonPath` (utilisée pour `DefinedBoard`) et `BalloonStraight` (utilisée pour `FreeBoard`), grâce à la méthode `move()`.

**BalloonPath.move() :**
- Récupérer la cellule cible actuelle dans le chemin
- Calculer la distance jusqu'au centre de cette cellule
- Si distance ≤ vitesse : atteindre la cellule et passer à la suivante
- Sinon : avancer de "vitesse" unités vers la cible
- Si fin du chemin : marquer comme inactif

**BalloonStraight.move() :**
- Avancer de "vitesse" unités dans la direction (UP/DOWN/LEFT/RIGHT)
- Vérifier si la position finale est atteinte
- Si oui : marquer comme inactif

**Gestion du temps :** Un compteur `currentTick` s'incrémente à chaque itération. À chaque tic, tous les ballons actifs appellent leur méthode `move()`. Lorsqu'un ballon termine, son temps de sortie est enregistré. La simulation continue tant qu'il reste au moins un ballon actif.

Difficultés rencontrées : La principale difficulté a été de concevoir l'architecture des classes de ballons et de définir comment gérer leur mouvement de manière fluide avec des vitesses différentes.

#### Commandes

```bash
# Compiler
javac -d classes src/board/*.java src/object/balloon/*.java

# Exécuter Livrable2a
java -cp classes board.Livrable2a

# Exécuter Livrable2b
java -cp classes board.Livrable2b

# Tests
javac -cp junit-console.jar:classes -d classes test/board/*.java test/object/balloon/*.java
java -jar junit-console.jar -cp classes --scan-class-path

# JARs
jar cvfe livrable2a.jar board.Livrable2a -C classes .
jar cvfe livrable2b.jar board.Livrable2b -C classes .
```

#### Difficultés restant à résoudre
Pour le moment, toutes les fonctionnalités du livrable 2 fonctionnent correctement.

---

### Livrable 3

#### Atteinte des objectifs

Les tours sont gérées par une hiérarchie de classes s'appuyant sur une classe mère abstraite `Tower`. Cette classe définit le comportement global des défenses grâce aux méthodes suivantes :

**Tower.findTarget() :** Parcourir la liste des ballons présents sur le plateau, vérifier si un ballon est actif et situé dans le rayon de portée, retourner le premier ballon valide comme cible.

**Tower.attack() :** Récupérer une cible via `findTarget()`, déterminer le type d'attaque selon l'effet de la tour (`EffectType`), infliger des dégâts immédiats ou générer un projectile.

**La classe Game** coordonne l'ensemble des entités et assure la progression automatique du jeu via la gestion du temps (`gameTick`) et l'automatisation (`autoPlayA` / `autoPlayB`).

#### Commandes

```bash
make classes     # Compilation
make tests       # Compilation des tests
make runtests    # Lancer les tests
make jar         # Création des Livrables
make clean       # Nettoyage

java -jar livrable3a.jar   # Livrable 3a
java -jar livrable3b.jar   # Livrable 3b
```

#### Difficultés restant à résoudre
Aucune difficulté majeure restante pour ce livrable.

---

### Livrable 4

#### Atteinte des objectifs

Le jeu se déroule en 10 manches successives. Pour les manches 1 à 5, une évolution est appliquée à chaque type de tour si cela est possible. Pour les manches 6 à 10, une évolution est supprimée de chaque type de tour si elles en possèdent.

**run10Manches()** : orchestre les 10 manches, appelle `gererEvolutionAleatoire(true/false)` selon la manche.

**gererEvolutionAleatoire()** : parcourt toutes les tours du plateau en évitant les doublons grâce à un ensemble de symboles déjà traités. Applique ou supprime une évolution aléatoire.

**afficherEvenementsBallons()** : compare l'état des ballons avant et après chaque tick pour détecter et afficher les événements (TOUCHE, DETRUIT, SORTI, RALENTI, ARRETE, REDEMARRE).

Difficultés rencontrées : gestion des évolutions sans doublons (deux tours de chaque type présentes), et sauvegarde de l'état des ballons avant chaque tick pour détecter les changements.

#### Commandes

```bash
make             # Compilation
make tests       # Compilation des tests
make runtests    # Lancer les tests
make jar         # Création des Livrables

java -jar livrable4a.jar   # Livrable 4a
java -jar livrable4b.jar   # Livrable 4b
```

#### Difficultés restant à résoudre
Le code présente encore de la duplication dans la gestion de la boucle principale. Une réorganisation de l'architecture est prévue pour les livrables suivants.

---

### Livrable 5

#### Atteinte des objectifs

Refonte majeure de l'architecture. La classe `Balloon` a été unifiée (suppression de `BalloonPath` et `BalloonStraight`). Introduction des classes `Player`, `Shop`, `Game`, `RoundManager`, `Clock`, `ListChooser` (interface), `HumanListChooser` et `AutoListChooser` pour séparer les responsabilités. Le joueur peut désormais choisir ses actions via un menu (acheter/vendre/évoluer une tour) en mode humain ou automatique.

##### amélioration de livrable

Dans ce livrable, nous avons effectué plusieurs améliorations importantes sur l’architecture du projet.

Tout d’abord, nous avons refactorisé la classe `Game` en la décomposant en plusieurs classes plus spécialisées, notamment `Round`, `RoundManager` ainsi que `Clock`. Cette décomposition permet une meilleure organisation du code et facilite sa maintenance.

La classe `Clock` a été enrichie avec l’intégration d’un thread, permettant de gérer le temps en arrière-plan, en parallèle du programme principal. Cela améliore la gestion du temps sans bloquer l’exécution du jeu.

Nous avons également ajouté un système de gestion des actions via la classe `ListChooser` et ses sous-classes :

- `HumanListChooser` (gestion des actions par un joueur humain)
- `AutoListChooser` (gestion automatique des actions)

Ce système permet de séparer clairement les comportements selon le type de joueur.

Enfin, nous avons poursuivi nos efforts de refactorisation en réduisant les duplications de code et en améliorant la modularité globale du projet.

#### Diagramme UML

```mermaid
classDiagram

%% ─── BOARD ──────────────────────────────────────────────────────────────────
    class Board {
        <<abstract>>
        #int width
        #int height
        #Cell[][] grid
        #Map~Integer_List~Cell~~ allPath
        #List~Tower~ towers
        #List~Balloon~ balloons
        #List~Projectil~ activeProjectiles
        #List~Balloon~ escapedBalloons
        #List~Cell~ allCells
        +Board(int height, int width)
        +calculerPath()*
        +init()*
        +getPathPositions()* List~Position~
        +allCells()* List~Cell~
        +isCellAvailable(int row, int col)* boolean
        +getCell(int row, int col) Cell
        +isInside(int row, int col) boolean
        +placeTower(Tower tower, Position position)
        +removeTower(Tower tower)
        +spawnBalloon(Balloon balloon)
        +update() List~Balloon~
        +getTowers() List~Tower~
        +getBalloons() List~Balloon~
        +getEscapedBalloons() List~Balloon~
        +clearEscapedBalloons()
        +getWidth() int
        +getHeight() int
        +getGrid() Cell[][]
        +getAllPath() Map~Integer_List~Cell~~
        +getActiveProjectiles() List~Projectil~
        +findAvailablePosition() Position
    }

    class DefinedBoard {
        -Position cheminAv
        -Position pointDeDepart
        +DefinedBoard(int height, int width)
        +init()
        +calculerPath()
        +getPathPositions() List~Position~
        +isCellAvailable(int row, int col) boolean
        +allCells() List~Cell~
        +getCheminLong() int
        +isBord(Position p) boolean
    }

    class FreeBoard {
        -int NBCHEMIN$
        -Map~Integer_Position~ numChemins
        -int cmp
        -Map~Position_Position~ chemins
        -int nbCheminsVoulus
        +FreeBoard(int height, int width, int nbChemin)
        +init()
        +calculerPath()
        +getPathPositions() List~Position~
        +isCellAvailable(int row, int col) boolean
        +allCells() List~Cell~
        +getDirection(Position depart, Position fin) Direction
        +setNbCheminsVoulus(int nb)
        +marquerCheminDroit(Position start, Position end)
        +getFinChemin(Position start) Position
        +retourneLesChemins(int num) Position[]
        +retourneNbrChemins() int
        +getCheminsMap() Map~Position_Position~
    }

    class Cell {
        -Position position
        -boolean isPath
        -List~Balloon~ balloons
        +Cell(Position position)
        +getPosition() Position
        +isPath() boolean
        +assignChemin()
        +estVide() boolean
        +addBalloon(Balloon b)
        +removeBalloon(Balloon b)
        +getBalloons() List~Balloon~
        +getNumBalloon() int
        +equals(Object o) boolean
    }

    class Position {
        -int row
        -int col
        +Position(int row, int col)
        +getRow() int
        +getCol() int
        +equals(Object obj) boolean
        +hashCode() int
        +toString() String
    }

    class Direction {
        <<enumeration>>
        UP
        DOWN
        RIGHT
        LEFT
    }

%% ─── ENTITY / BALLOON ───────────────────────────────────────────────────────
    class Balloon {
        #int id
        #double posColonne
        #double posLigne
        #double speed
        #int health
        #BalloonType type
        #boolean active
        #boolean escaped
        #int exitTime
        #int freezeTimer
        #int slowTimer
        #double slowFactor
        #List~Position~ path
        #int pathIndex
        -double progression
        +Balloon(int id, double startLigne, double startColonne, BalloonType type)
        +move() boolean
        +hit(int damage) boolean
        +update()
        +freeze(int duration)
        +slow(double factor, int duration)
        +remove()
        +hasEscaped() boolean
        +isActive() boolean
        +getEffectiveSpeed() double
        +getId() int
        +getSpeed() double
        +getHealth() int
        +getType() BalloonType
        +getExitTime() int
        +getColonne() double
        +getLigne() double
        +getPath() List~Position~
        +setPath(List~Position~ path)
        +setStartPosition(double ligne, double colonne)
    }

    class BalloonType {
        <<enumeration>>
        BASIC
        MEDIUM
        STRONG
        +int health
        +double speed
        +int reward
    }

%% ─── ENTITY / TOWER ─────────────────────────────────────────────────────────
    class Tower {
        <<abstract>>
        #Position position
        #ProjectilType type
        #int cout
        #int porte
        #int cadence
        #boolean evolution
        #EffectType effectType
        #Evolution evolutions
        #int degatsSup
        #int ticksSinceLastShot
        +Tower(Position, int cout, int porte, int cadence, boolean evolution, EffectType, ProjectilType)
        +update(List~Balloon~ balloons, List~Projectil~ activeProjectiles)
        +attack(List~Balloon~ balloons)* Projectil
        +appliquerEvolution(TypeEvolution typeEvo)* int
        +getCoutEvolution(TypeEvolution evolution)* int
        +getSymbol()* String
        +getName()* String
        +findTarget(List~Balloon~ balloons) Balloon
        +distanceTo(Balloon b) double
        +getPosition() Position
        +getPorte() int
        +getEvolution() boolean
        +getEvolutions() Evolution
        +getCost() int
        +SellEvolution(TypeEvolution evo) int
        +getSellPrice() int
        +setPosition(Position position)
        +getrow() double
        +getcol() double
    }

    class DartMonkey {
        +DartMonkey(Position pos)
        +attack(List~Balloon~ balloons) Projectil
        +appliquerEvolution(TypeEvolution typeEvo) int
        +getCoutEvolution(TypeEvolution typeEvo) int
        +getSymbol() String
        +getName() String
    }

    class BombTower {
        +BombTower(Position pos)
        +attack(List~Balloon~ balloons) Projectil
        +appliquerEvolution(TypeEvolution typeEvo) int
        +getCoutEvolution(TypeEvolution typeEvo) int
        +getSymbol() String
        +getName() String
    }

    class IceTower {
        +IceTower(Position pos)
        +attack(List~Balloon~ balloons) Projectil
        +appliquerEvolution(TypeEvolution typeEvo) int
        +getCoutEvolution(TypeEvolution typeEvo) int
        +getSymbol() String
        +getName() String
    }

    class SlowTower {
        +SlowTower(Position pos)
        +attack(List~Balloon~ balloons) Projectil
        +appliquerEvolution(TypeEvolution typeEvo) int
        +getCoutEvolution(TypeEvolution typeEvo) int
        +getSymbol() String
        +getName() String
    }

    class TackShooter {
        +TackShooter(Position pos)
        +attack(List~Balloon~ balloons) Projectil
        +appliquerEvolution(TypeEvolution typeEvo) int
        +getCoutEvolution(TypeEvolution typeEvo) int
        +getSymbol() String
        +getName() String
    }

    class EliteSniper {
        +EliteSniper(Position pos)
        +attack(List~Balloon~ balloons) Projectil
        +appliquerEvolution(TypeEvolution typeEvo) int
        +getCoutEvolution(TypeEvolution typeEvo) int
        +getSymbol() String
        +getName() String
    }

    class Gorilla {
        +Gorilla(Position pos)
        +attack(List~Balloon~ balloons) Projectil
        +appliquerEvolution(TypeEvolution typeEvo) int
        +getCoutEvolution(TypeEvolution typeEvo) int
        +getSymbol() String
        +getName() String
    }

    class EffectType {
        <<enumeration>>
        IMMEDIATE
        AREA
        SLOW
        FREEZE
    }

    class Evolution {
        -ArrayList~TypeEvolution~ typeEvolutions
        -HashMap~TypeEvolution_Integer~ evolutionsPossedees
        +Evolution()
        +addEvolution(TypeEvolution typeEvolution)
        +deleteEvolutions(TypeEvolution typeEvolutions)
        +getEvolutions() ArrayList~TypeEvolution~
        +vendreEvolution(TypeEvolution typeEvolution)
        +getEvolutionsPossedees() HashMap~TypeEvolution_Integer~
    }

    class TypeEvolution {
        <<enumeration>>
        PORTEE
        CANDENCE
        PUISSANCE
        PROJECTILES
    }

%% ─── ENTITY / PROJECTIL ─────────────────────────────────────────────────────
    class Projectil {
        <<abstract>>
        #double x
        #double y
        #Balloon target
        #ProjectilType type
        #double speed
        #boolean active
        +Projectil(ProjectilType type, double startX, double startY, Balloon target)
        +update()
        +triggerImpact()*
        +isActive() boolean
    }

    class ProjectilSimple {
        +ProjectilSimple(ProjectilType, double startX, double startY, Balloon target)
        +triggerImpact()
    }

    class ProjectilBombe {
        -List~Balloon~ balloons
        -double explosionRadius
        +ProjectilBombe(ProjectilType, double startX, double startY, Balloon target, double radius, List~Balloon~ currentBalloons)
        +triggerImpact()
    }

    class ProjectilFreeze {
        +ProjectilFreeze(ProjectilType, double startX, double startY, Balloon target)
        +triggerImpact()
    }

    class ProjectilSlow {
        +ProjectilSlow(ProjectilType, double startX, double startY, Balloon target)
        +triggerImpact()
    }

    class ProjectilType {
        <<enumeration>>
        NO_DAMAGE
        FLECHETTE
        FLECHETTE_POINTUE
        FLECHETTE_TRES_POINTUE
        AIGUILLE
        BOMBE
        EXTRA_BOMBE
        -int degats
        +getDegats() int
    }

    class NotMatchException {
        +NotMatchException(String message)
        +getMessage() String
    }

%% ─── CLOCK ──────────────────────────────────────────────────────────────────
    class Clock {
        -List~ClockListener~ listeners
        -int fps
        -int tickCount
        -boolean running
        -Thread thread
        +Clock(int fps)
        +addListener(ClockListener listener)
        +removeListener(ClockListener listener)
        +reset()
        +start()
        +stop()
        +getTickCount() int
        +getFps() int
    }

    class ClockListener {
        <<interface>>
        +onTick(int tickCount)
    }

%% ─── CHOOSE ─────────────────────────────────────────────────────────────────
    class ListChooser~T~ {
        <<interface>>
        +choose(String p, List~T~ options) T
    }

    class HumanListChooser~T~ {
        -Scanner scanner
        +choose(String prompt, List~T~ options) T
    }

    class AutoListChooser~T~ {
        -Random random
        -int index
        +choose(String p, List~T~ options) T
    }

    class Action {
        <<abstract>>
        +execute(GameContext game)*
        +getDescription()* String
        +isAllowedForAuto() boolean
    }

    class ActionBuilder {
        +buildMenu(Player player, Shop shop)$ List~Action~
        +buildAcheterTour(Player player, Shop shop)$ Action
        +buildEvoluerTour(Player player)$ Action
        +buildVendreTour(Player player)$ Action
        +buildVendreEvolution(Player player)$ Action
    }

    class BuyTowerAction {
        -Tower tower
        -Board board
        -ListChooser~Position~ positionChooser
        +BuyTowerAction(Tower t, Board board, ListChooser~Position~ positionChooser)
        +execute(GameContext game)
        +getDescription() String
    }

    class SellTowerAction {
        -Tower tower
        +SellTowerAction(Tower tower)
        +execute(GameContext game)
        +getDescription() String
    }

    class Upgrade {
        -Tower tower
        -TypeEvolution typeEvo
        +Upgrade(Tower t, TypeEvolution typeEvo)
        +execute(GameContext game)
        +getDescription() String
    }

    class DownGrade {
        -Tower tower
        -TypeEvolution evo
        +DownGrade(Tower tower, TypeEvolution evo)
        +execute(GameContext game)
        +getDescription() String
    }

    class MenuAction {
        -String des
        -List~Action~ actions
        +MenuAction(String des, List~Action~ actions)
        +execute(GameContext ctx)
        +getDescription() String
    }

    class DoNothingAction {
        +execute(GameContext game)
        +getDescription() String
    }

    class QuitAction {
        +execute(GameContext ctx)
        +getDescription() String
        +isAllowedForAuto() boolean
    }

    class RetourAction {
        +execute(GameContext ctx)
        +getDescription() String
    }

%% ─── PLAYER ─────────────────────────────────────────────────────────────────
    class Player {
        +int INITIAL_LIVES$
        -int credit
        -List~Tower~ towers
        -ListChooser~Action~ actionChooser
        -ListChooser~Position~ positionChooser
        -int lives
        +Player(int credit, ListChooser~Action~, ListChooser~Position~)
        +buyTower(Tower tower)
        +sellTower(Tower tower)
        +chooseAction(List~Action~ actions, GameContext ctx) Action
        +runActionPhase(GameContext ctx, Shop shop)
        +loseLife()
        +isAlive() boolean
        +getLives() int
        +setLives(int lives)
        +getCredit() int
        +addCredit(int amount)
        +removeCredit(int amount)
        +getTowers() List~Tower~
        +setCredit(int i)
        +getPositionChooser() ListChooser~Position~
    }

    class Shop {
        -Board board
        +Shop(Board board)
        +getBoard() Board
        +getAvailableTowers() List~Tower~
    }

%% ─── ROUND ──────────────────────────────────────────────────────────────────
    class Round {
        -int number
        -List~Balloon~ balloons
        -boolean state
        -List~Balloon~ escapedBalloons
        +Round(int number, List~Balloon~ balloons)
        +getNumber() int
        +getBalloons() List~Balloon~
        +getState() boolean
        +addEscaped(Balloon b)
        +getEscapedBalloons() List~Balloon~
    }

    class RoundManager {
        -List~Round~ rounds
        -Board board
        -Player player
        -int currentIndex
        -int spawnTick
        -boolean roundStarted
        -boolean roundJustFinished
        -int escapedCount
        -boolean firstTick
        -int SPAWN_INTERVAL$
        +RoundManager(List~Round~ rounds, Board board, Player player)
        +update(int tickCount) List~Balloon~
        +advanceRound()
        +isFinished() boolean
        +hasPlayerLost() boolean
        +getCurrentIndex() int
        +isCurrentRoundFinished() boolean
        +getEscapedCount() int
        +getRounds() List~Round~
    }

%% ─── GAME ───────────────────────────────────────────────────────────────────
    class GameContext {
        <<interface>>
        +getPlayer() Player
        +getBoard() Board
    }

    class Game {
        -Board board
        -Player player
        -RoundManager roundManager
        -Clock clock
        -boolean isRunning
        -BoardRenderer renderer
        +Game(Player player, Board board, List~Round~ rounds, int fps)
        +onTick(int tickCount)
        +runOneRound()
        +start()
        +stop()
        +getPlayer() Player
        +getBoard() Board
        +getRoundCount() int
        +hasPlayerLost() boolean
        +getEscapedCount() int
    }

    class GameContext {
        <<interface>>
        +getPlayer() Player
        +getBoard() Board
    }

    class GameSetup {
        -int NB_ROUNDS$
        -int FPS$
        -Board board
        -Player player
        -Shop shop
        -Game game
        -int nbBallons
        -boolean isHuman
        +GameSetup(int hauteur, int largeur, int nbBallons, String typePlateau, String typeJoueur)
        +getBoard() Board
        +getPlayer() Player
        +getShop() Shop
        +getGame() Game
        +isHuman() boolean
    }

    class GameLoop {
        -int REWARD_PER_ROUND$
        -Game game
        -Player player
        -Shop shop
        -boolean isHuman
        -BotStrategy bot
        +GameLoop(GameSetup setup)
        +start()
    }

    class BotStrategy {
        -Player player
        -Shop shop
        -Random rng
        +BotStrategy(Player player, Shop shop)
        +placeInitialTowers(Game game)
        +applyRoundEvolution(int roundNum, Game game)
    }

%% ─── UI ─────────────────────────────────────────────────────────────────────
    class BoardRenderer {
        +render(Board board)
        +buildGrid(Board board) String[][]
        +printGrid(String[][] display, Board board)
    }

    class GameRenderer {
        +printBanner(Player player)$
        +printRoundHeader(int roundNum, Player player)$
        +printGameOver()$
        +printVictory(int lives)$
        +printRoundResult(int escaped, Player player, int reward)$
        +printInitialBuyPhase()$
        +printActionPhase()$
    }

%% ─── LIVRABLES ──────────────────────────────────────────────────────────────
    class Livrable6 {
        +main(String[] args)$
        -parseArgs(String[] args)$ int[]
        -printUsage()$
    }

    class LauncherAInteractive {
        +main(String[] args)$
    }

    class LauncherARandom {
        +main(String[] args)$
    }

    class LauncherBInteractive {
        +main(String[] args)$
    }

    class LauncherBRandom {
        +main(String[] args)$
    }

%% ─── RELATIONS ──────────────────────────────────────────────────────────────

    Board <|-- DefinedBoard
    Board <|-- FreeBoard
    Board "1" *-- "many" Cell
    Cell "1" *-- "1" Position

    Balloon --> Position
    Balloon --> BalloonType

    Tower <|-- DartMonkey
    Tower <|-- BombTower
    Tower <|-- IceTower
    Tower <|-- SlowTower
    Tower <|-- TackShooter
    Tower <|-- EliteSniper
    Tower <|-- Gorilla
    Tower --> Position
    Tower --> EffectType
    Tower --> ProjectilType
    Tower --> Evolution
    Evolution --> TypeEvolution

    Projectil <|-- ProjectilSimple
    Projectil <|-- ProjectilBombe
    Projectil <|-- ProjectilFreeze
    Projectil <|-- ProjectilSlow
    Projectil --> Balloon
    Projectil --> ProjectilType
    ProjectilBombe ..> NotMatchException
    ProjectilSimple ..> NotMatchException

    ClockListener <|.. Game
    Clock --> ClockListener

    ListChooser <|.. HumanListChooser
    ListChooser <|.. AutoListChooser

    Action <|-- BuyTowerAction
    Action <|-- SellTowerAction
    Action <|-- Upgrade
    Action <|-- DownGrade
    Action <|-- MenuAction
    Action <|-- DoNothingAction
    Action <|-- QuitAction
    Action <|-- RetourAction
    BuyTowerAction --> Tower
    BuyTowerAction --> Board
    SellTowerAction --> Tower
    Upgrade --> Tower
    Upgrade --> TypeEvolution
    DownGrade --> Tower
    DownGrade --> TypeEvolution
    ActionBuilder ..> Action

    Player --> ListChooser
    Player --> Tower
    Shop --> Board
    Shop ..> Tower

    Round --> Balloon
    RoundManager --> Round
    RoundManager --> Board
    RoundManager --> Player

    GameContext <|.. Game
    Game --> Board
    Game --> Player
    Game --> RoundManager
    Game --> Clock
    Game --> BoardRenderer
    GameSetup --> Board
    GameSetup --> Player
    GameSetup --> Shop
    GameSetup --> Game
    GameLoop --> Game
    GameLoop --> Player
    GameLoop --> Shop
    GameLoop --> BotStrategy
    GameLoop --> GameRenderer
    BotStrategy --> Player
    BotStrategy --> Shop

    Livrable6 ..> GameSetup
    Livrable6 ..> GameLoop
    LauncherAInteractive ..> Livrable6
    LauncherARandom ..> Livrable6
    LauncherBInteractive ..> Livrable6
    LauncherBRandom ..> Livrable6
```

#### Commandes

```bash
make clean       # pour supprimer les fichier .class s'il en reste encore
make             # Compilation
make jar         # Création des Livrables

java -jar livrable5.jar   # Livrable5
```
#### Problèmes rencontrés
Malgré ces améliorations, plusieurs difficultés ont été rencontrées :

- Intégration de `ListChooser` :
  Nous avons hésité sur son emplacement dans l’architecture (dans `Player`, `Game` ou `Round`), ce qui a rendu son intégration complexe.
- Gestion de la classe `Clock` :
  Son intégration dans le jeu a été difficile, notamment à cause de la gestion du temps. Après des recherches, nous avons opté pour une solution basée sur les threads.
- Attribution des responsabilités (`Balloon`, `Tower`, etc.) :
  Il a été compliqué de déterminer dans quelles classes placer certains attributs et responsabilités, ce qui a posé des problèmes de conception.
- Joueur automatique :
  Le comportement du joueur automatique (introduit dans le livrable 5) reste problématique et nécessite encore des améliorations.
- Gestion du `Board` :
  L’ajout de nouveaux types de Balloon et la gestion des différentes actions et sous-actions ont complexifié la logique du plateau.
- Découplage des classes :
  Supprimer les dépendances directes entre :
    - `Game` et `Player`
    - `Clock` et `Game`
      a été un défi important. Nous avons introduit des interfaces (`GameContext`, `ClockListener`) pour améliorer le découplage, mais cela a ajouté de la complexité.

#### Difficultés restant à résoudre
Malgré les améliorations apportées, certaines difficultés restent à résoudre :
- Joueur automatique :
  Le comportement du joueur automatique n’est pas encore totalement fonctionnel ni optimal. Certaines décisions prises ne sont pas toujours cohérentes avec l’état du jeu, ce qui nécessite encore des ajustements au niveau de la logique et de la stratégie.
- Gestion des vies :
  Un bug persiste concernant la décrémentation des vies : dans certains cas, le dernier `Balloon` qui sort du `Board` ne provoque pas la diminution du nombre de vies restantes. Cela suggère un problème dans la détection de sortie ou dans le déclenchement de l’événement associé.

---

### Livrable 6

#### Atteinte des objectifs

Ce livrable finalise le projet en proposant un jeu Tower Defense complet et jouable, intégrant l'ensemble des fonctionnalités développées lors des livrables précédents. L'architecture a été consolidée autour de nouvelles classes dédiées à la création et à la gestion du jeu.

##### Point d'entrée unifié et Launchers

La classe `Livrable6` sert de point d'entrée unique. Elle analyse les arguments en ligne de commande pour déterminer le type de plateau (`a` pour `DefinedBoard`, `b` pour `FreeBoard`), le mode de jeu (`interactive` ou `random`), ainsi que les dimensions et le nombre de chemins. Quatre classes Launcher simplifient le lancement :
- `LauncherAInteractive` : plateau A en mode interactif
- `LauncherARandom` : plateau A en mode automatique
- `LauncherBInteractive` : plateau B en mode interactif
- `LauncherBRandom` : plateau B en mode automatique

##### GameFactory

La classe `GameFactory` centralise la construction du jeu. Elle instancie le plateau (selon le type choisi), le joueur , le `ListChooser` adapté au mode (humain ou automatique), et génère 10 rounds contenant au moins 15 ballons répartis entre les types `BASIC`, `MEDIUM` et `STRONG`. Le tout est assemblé dans un objet `Game`.

##### GameLoop

La classe `GameLoop` orchestre la boucle principale du jeu en alternant deux phases :
1. **Phase d'action** : le joueur interagit via un menu (acheter/vendre/évoluer une tour, ne rien faire, quitter) construit par `ActionBuilder`.
2. **Phase de combat** : `Game.runOneRound()` fait avancer la simulation tick par tick — les tours tirent sur les ballons à portée, les projectiles se déplacent, les ballons avancent le long du chemin, et les ballons détruits rapportent des crédits.

La partie se termine par un Game Over si le joueur perd toutes ses vies, ou par une victoire s'il survit aux 10 rounds.

##### Affichage console (UI)

Deux nouvelles classes gèrent l'affichage :
- `BoardRenderer` : affiche la grille du plateau à chaque tick .
- `GameStateRenderer` : affiche les informations détaillées à chaque tick (numéro du tick, vies, crédits, position et HP de chaque ballon) ainsi que les messages de début/fin de round, de Game Over et de victoire.

##### Boutique et tours

La classe `Shop` propose 7 types de tours . Chaque tour possède des évolutions possibles que le joueur peut acheter ou revendre via le menu d'actions.

En mode interactif, le joueur choisit la position de placement de chaque tour. En mode automatique, une position disponible est sélectionnée aléatoirement par le système.

#### Commandes

```bash
make clean       # Nettoyage des fichiers .class et JARs
make             # Compilation de toutes les classes
make tests       # Compilation des tests
make runtests    # Lancer les tests
make jar         # Création des 4 JARs

# Plateau A (DefinedBoard) - mode interactif
java -jar jar/towerdefense-a-interactive.jar <largeur> <hauteur>

# Plateau A (DefinedBoard) - mode automatique
java -jar jar/towerdefense-a-random.jar <largeur> <hauteur>

# Plateau B (FreeBoard) - mode interactif
java -jar jar/towerdefense-b-interactive.jar <largeur> <hauteur> <nbChemins>

# Plateau B (FreeBoard) - mode automatique
java -jar jar/towerdefense-b-random.jar <largeur> <hauteur> <nbChemins>
```

#### Difficultés restant à résoudre


Il reste des améliorations à faire dans certaines classes. Quelques méthodes sont encore trop longues, donc on doit les simplifier. On a encore un peu de code dupliqué à corriger.
- Joueur automatique : le comportement du joueur automatique (`AutoListChooser`) n'est pas encore optimal
- Il reste des améliorations à faire dans certaines classes. Quelques méthodes sont encore trop longues, donc on doit les simplifier. On a encore un peu de code dupliqué à corriger.
- Ajout de tests 


---

## Journal de bord

### Semaine 1
Ce qui a été réalisé

pour l'object Board  peut etre une classe abstract , il ya deux sous classes  , une avec un chemin precis  ,
défini pour la trajectoire des ballons, on va definir le debut et la fin du chemin nous meme  ,
on commence par la case debut on a le droit de choisir entre  3 cases voisines (inspiration DSI  Chaque tuile
du joueur a donc nécessairement 4 tuiles voisines (ou emplacements voisins)), sans la tuile ou le chemin est deja passe
avec une fonction random , et a chaque fois que le tuile de chemin est cree ( on a choisi une case ), on oublie la colonne d'avant
et donc peut importe le nombre de cases de notre board ca va nous cree un chemin.
pour deuxieme mode on choisi dabord le nombre de ballons qu'on va repartir en deux groupes , des ballons verticale ou horizontale ,puis on va
choisir les chemins de maniere random , qui va choirir la case de debut pour chauque ballon , et la on va choisir le cote aussi de la meme façon
car le ballon il peut sortir des deux cote
pour le horloge , on fait avec une fonction comme thrad.sleep, et a chaque toc d'orloge on definie la vitesse du ballon ,
pour se deplacer d'une case a une autre on fait en sorte que chaque ballon prenne un temps x pour avancer a lautre case
Difficultés rencontrées

le chemin, le clock
Objectifs pour la semaine et répartition du travail par membre

diagramme uml complet, algo des chemins
pour la repartion du travail on a pas eu le temps de discuter on a creer un groupe pour justement en discuter
### Semaine 2
Ce qui a été réalisé

Diagramme UML presque complet des Boards, et d'autres classes dont on aura besoin. L'algo des chemins. Pour le 1 er Board ,
on choisi pas cas de la fin, et pour le deuxieme Board on va generer des chemins qui vont a toutes les directions , pas seulement de gauche a droite
Difficultés rencontrées

le temps, pour synchroniser les ballons et les tours
Objectifs pour la semaine et répartition du travail par membre

finir le diagramme UML, pseudo code des chemins , finaliser le premier livrable

### Semaine 3
Ce qui a été réalisé

on a finalisé le diagramme uml pour le board (sous reserve de modifications) on reste sur la meme idée d'une classe abstraite Board et deux sous classes DefinedBoard et FreeBoard
ce qui nous permettra de créer des extensions de plateaux
pour le DefinedBoard donc le plateau avec un chemin definie notre algo choisi lui meme aleatoirement la case du debut de n'importe quel bord
ensuite pour la construction du chemin il choisit une de ses cases voisines, la condition d'arret est : si on touche le bord n'importe lequel
le chemin est fini donc le chemin ne peut pas etre sur les bords apart pour le debut et la fin
pour le FreeBoard donc le plateau avec un chemin non precis pour le moment on a commencer avec un pseudo code donc c'est la case du debut choisi aleatoirement
qui decide de la trajectoire du chemin par exemple si elle est sur le bord d'en haut le chemin va tout droit vers le board du bas, si ça commence a gauche ça va vers la droite et inversement
on a aussi implementé des classe Cell et position pour mieux gérer les cases
Difficultés rencontrées

le code exact pour l'algo des deux chemins
Objectifs pour la semaine et répartition du travail par membre

finir le livrable 1 donc : la creation des deux types de plateaux et le calcul des chemins
pour le repartion on mettra a jour le readme durant la semaine du travail de chaque membre

### Semaine 4
**Ce qui a été réalisé :** Suite du diagramme UML avec une classe `Balloon`, `Round`, un enum `Level` pour les niveaux de résistance, et une classe horloge pour gérer les tics.

**Difficultés rencontrées :** Gérer les tics d'horloge et la gestion du déplacement des ballons sur les plateaux.

**Objectifs pour la semaine suivante :** Finir le livrable 2 et entamer le livrable 3.

---

### Semaine 5
**Ce qui a été réalisé :** Pseudo diagramme pour le livrable 3. Diagramme complet finalisé (voir Livrable 5).

**Difficultés rencontrées :** Gestion du temps.

**Objectifs pour la semaine suivante :** Revoir la structure des classes, réduire le code des mains et leurs paquetages.

---

### Semaine 6
**Ce qui a été réalisé :** Livrable 3.

**Difficultés rencontrées :** *(à compléter)*

**Objectifs pour la semaine suivante :** *(à compléter)*

---

### Semaine 7
**Ce qui a été réalisé :** Correction du Makefile et des classes de tests. Discussion sur le livrable 4.

**Difficultés rencontrées :** *(à compléter)*

**Objectifs pour la semaine suivante :** Avancer au maximum dans le livrable 4 : créer la classe `Evolution` avec les tests, ajouter les évolutions dans le game.

---

### Semaine 8
**Ce qui a été réalisé :** Classe `Evolution` et `TypeEvolution` pour gérer les évolutions, avec les classes de tests.

**Difficultés rencontrées :** *(à compléter)*

**Objectifs pour la semaine suivante :** Finaliser le livrable 4.

---

### Semaine 9
Discussion générale
Nous avons soulevé des problèmes conceptuels, notamment par rapport à l’approche adoptée lors des commits précédents, qui se rapproche plus du fonctionnel que de la conception orientée objet.
Il est nécessaire de :
¤  Détailer le README.
¤  Gérer l’affichage des plateaux (tout ne doit pas être géré dans la classe   Game).
¤  Pour les ballons, il faut avoir une seule classe Ballon et gérer leurs comportements ailleurs.
¤  Découper les méthodes play(Board b) pour éviter la duplication de code.
¤  Prévoir un exemple de chooseTower pour choisir les tours soit manuellement, soit aléatoirement.
¤  Supprimer les méthodes séparées de play et centraliser leur gestion dans play(Board b).
¤  Gérer également la duplication de code dans les classes Projectil.
Ce qui a été réalisé

Nous avons travaillé sur le début du livrable 5, en mettant en place la structure générale. Nous essayons de gérer au mieux la duplication de code, sans toutefois impacter le fonctionnement du livrable 4.
Difficultés rencontrées

¤  Duplication de code dans plusieurs classes.
¤  Suivi d’une approche orientée objet.
Objectifs pour la semaine et répartition du travail par membre

Améliorer au mieux la duplication de code.
Finaliser le livrable 4.
Gérer les actions du joueur.

### Semaine 10
**Ce qui a été réalisé :** Refonte de la classe `Balloon` pour simplifier sa conception (suppression de la séparation selon les deux types de plateaux, réécriture en version plus POO).

**Difficultés rencontrées :** *(à compléter)*

**Objectifs pour la semaine suivante :** Révision de certaines méthodes, finir le livrable 5, faire plus de tests.

---

### Semaine 11
**Ce qui a été réalisé :
- Livrable 5 deja fini
- Ajouter les ListChooser, les actions du joueur, les tests, aussi que le clock
pour le jeu afffiche a chaque tick d'horloge
- Ajouter les classes UI simple du jeu

**Difficultés rencontrées :les tours sont place aleatoirement sans demande au joueur de choisir la position, et quelque petit bugs ( le dernier ballon quitte le jeu ne diminue pas la vie, il y a
des ballons sorties mais le jeu dit encore parfait)

**Objectifs pour la semaine suivante :Fixer les petits bugs et completer le projet

### Semaine 12 : 
**Ce qui a ete realise : 

  - Finalisation du livrable 6.
  - Ajout de GameFactory pour centraliser l’initialisation du jeu.
  - Ajout de GameLoop pour clarifier la boucle principale.
  - Ajout/validation des launchers pour exécuter facilement les variantes A/B en mode interactif ou aléatoire.
  
**Difficultés rencontrées :

  - Ajustement des responsabilités entre classes (initialisation, boucle de jeu, actions joueur).
  - Débogage de cas limites liés aux ballons qui s’échappent et à la mise à jour des vies.

---

### Semaine 12
**Ce qui a été réalisé :
- Le jeu complet est fini

**Difficultés rencontrées :
- Il reste encore les petits erreurs dans l'operation du code ( code trop long, difficile a lire, 
le jeu n'arrete pas quand un round fini dans mode auto)

**Objectifs pour finaliser le projet :
