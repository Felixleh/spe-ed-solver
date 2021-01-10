package simulation;

import utility.game.player.*;
import utility.geometry.Point2i;
import utility.geometry.Vector2i;

public class SimulationPlayer extends MovablePlayer {

    private int playerId;
    private Point2i position;
    private PlayerDirection direction;
    private PlayerAction lastSetAction;
    private boolean isActive;
    private int speed;

    public SimulationPlayer(int playerId, Point2i initialPosition, PlayerDirection initialDirection, int initialSpeed) {
        this(playerId, initialPosition, initialDirection, initialSpeed, 1);
    }

    public SimulationPlayer(int playerId, Point2i initialPosition, PlayerDirection initialDirection, int initialSpeed,
            int initialRound) {
        super(initialRound);
        this.playerId = playerId;
        this.position = initialPosition;
        this.direction = initialDirection;
        this.speed = initialSpeed;
        this.isActive = true;
    }

    @Override
    public int getPlayerId() {
        return playerId;
    }

    @Override
    public void die() {
        this.isActive = false;
    }

    @Override
    public boolean isActive() {
        if (this.isActive && (getSpeed() > MAX_SPEED || getSpeed() < MIN_SPEED))
            die();

        return this.isActive;
    }

    @Override
    public PlayerDirection getDirection() {
        return this.direction;
    }

    @Override
    public int getSpeed() {
        return this.speed;
    }

    @Override
    public Point2i getPosition() {
        return position;
    }

    public boolean isReadyToMove() {
        return this.lastSetAction != null || !this.isActive();
    }

    @Override
    /**
     * Sets the to do action. The Player dies, if an action is already set.
     */
    public void setNextAction(PlayerAction action) {
        // in the simulation it is not allowed to overwrite the set action
        if (this.lastSetAction != null)
            die();

        if (isActive())
            this.lastSetAction = action;
    }

    @Override
    public PlayerAction getNextAction() {
        return this.lastSetAction;
    }

    @Override
    /**
     * Applies the {@link SimulationPlayer#lastSetAction} to the Player, if the
     * player is alive and resets the Action
     */
    public void doAction() {
        if (!isActive())
            return;

        if (this.lastSetAction == PlayerAction.SPEED_UP)
            this.speed++;
        else if (this.lastSetAction == PlayerAction.SLOW_DOWN)
            this.speed--;
        else
            this.direction = this.direction.doAction(this.lastSetAction);

        this.lastSetAction = null;
    }

    @Override
    /**
     * Changes the Players Position depending on direction and speed, if the player
     * is alive
     */
    public void doMove() {
        if (!isActive())
            return;

        final Vector2i speedDirectionVector = this.direction.getDirectionVector().multiply(this.speed);
        this.position = this.position.translate(speedDirectionVector);
    }

    @Override
    public SimulationPlayer copy() {
        return new SimulationPlayer(this.playerId, this.position, this.direction, this.speed, this.getRound());
    }
}
