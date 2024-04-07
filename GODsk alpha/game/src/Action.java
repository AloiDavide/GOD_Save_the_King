import java.util.concurrent.TimeUnit;

public enum Action {
    ATTACK{
        public void execute(Character active, Character target){
            //attacco
            int damage = active.getStat("atk") - target.getStat("def");
            if (damage<1) damage = 1;
            target.setHP(target.getHP() - damage);
            info.printThis(active.name + " -> " + target.name + ": " + damage + " danni");

            if(target.getHP() <= 0){
                active.gainExp(expFormula(active, target));
                info.printThis(target.name + " è morto/a");
                target.setHP(0);
                Game.getGameInstance().killCharacter(target);
                return;
            }

            //contrattacco specchiato, solo se l'attaccante è in range dell'avversario.
            int distance = active.getPosition().distanceTo(target.getPosition());
            if(distance <= target.getStat("atkRangeMax") && distance >= target.getStat("atkRangeMin")){
                int counterDamage = target.getStat("atk") - active.getStat("def");
                if (counterDamage<1) counterDamage = 1;
                active.setHP(active.getHP() - counterDamage);
                info.printThis(active.name + " subisce un contrattacco.");
                info.printThis(target.name + " -> " + active.name + ": " + damage + " danni");

                if(active.getHP() <= 0){
                    target.gainExp(expFormula(target, active));
                    info.printThis(active.name + " è morto/a");
                    active.setHP(0);
                    Game.getGameInstance().killCharacter(active);
            }

            }
        }
    },
    WAIT{
        public void execute(Character active, Character target){
            info.printThis(active.name + " aspetta.");
        }

    },
    HELP{
        public void execute(Character active, Character target){
            target.isDown = false;
            target.setHP(1);
            info.printThis(target.name + " si rialza con l'aiuto di "+ active.name);
            Game.getGameInstance().refreshTurnOrder();
            active.gainExp(5);
            target.gainExp(5);
        }
    },
    HEAL{
        public void execute(Character active, Character target){
            target.isDown = false;
            Game.getGameInstance().refreshTurnOrder();
            int cura = target.getStat("maxHP")/3;
            int tot = target.getHP() + 10;
            target.getStat("maxHP");
            if(tot > target.getStat("maxHP")){
                target.setHP(target.getStat("maxHP"));
            }
            else { target.setHP(tot);
            }

            info.printThis(active.name + "cura "+ target.name + " di "+cura+"HP");
            active.gainExp(10);
        }
    };

    public InfoPanel info = InfoPanel.getInfoPanel();

    abstract void execute(Character active, Character target);

    public static int expFormula(Character active, Character target){
        double modifier = (double) target.level/active.level;
        return (int)Math.round(30 * modifier);
    }

}
