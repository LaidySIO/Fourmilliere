# Fourmillière 
![](src/com/fourmilliere/Files/queen.png)

### Lancement de l'application : 

  - Lancer MainFourmilliere.main()
  - Saisir les parametres : 
    - Nombre de cases
    - Nombre de colonie
    - % de rareté des rassources
    
### Fonctionnement 

Une colonie de fourmis est composée des membres suivants :

- La reine
- Les ouvrières
- Les guerrières

##### La reine

- Il n'en existe qu'une seule par colonie
- Elle ne sait pas se déplacer
- En début de simulation ou lorsque les ouvrières lui apportent de l'eau et de la nourriture, la reine met au monde 3 nouvelles fourmis. Dont au moins une guerrière et une ouvrière dans le groupe.
- En début de simulation, 1 ou plusieurs reines apparaissent aléatoirement sur la carte, elles formeront des colonies adverses.
- La reine meurt si elle est attaquée

##### Les ouvrières

- Elles cherchent de l'eau et de la nourriture
- Elles retournent donner leur chargement à la reine dès qu'elles trouvent quelque chose
- Si la reine meurt, elles errent jusqu'à ce qu'elles rencontent une guerrière

##### Les Guerrières

- Elles cherchent les fourmis adverses et les attaquent
- Lorsqu'une guerrière rencontre une autre guerrière, la gagnante est déterminée aléatoirement
- Lorsqu'une guerrière rencontre une ouvrière, l'ouvrière meurt
- Lorsqu'une guerrière rencontre une reine, la reine meurt

### Informations technique et fonctionnelles


 ##### Language 
  - Java
 #####Rendu en console 
  - Java Swing 
 
 #####Fonction principales de l'application 
 #####generate()
 - Permet de générer les element swing au lancement du jeu 
 #####seDeplacer()
 - Déplace une fourmi d'une case à l'autre 
 #####getDirectionPossible()
 - Liste de positions qui déterminent les directions possible pour une fourmi
 #####rentrer()
 - Liste de positions qui déterminent la meilleur direction possible pour qu'une ouvriere donne à manger à la reine
 #####donnerVie()
 - La reine donne naissance a des fourmi s'il y a de la place

 

 