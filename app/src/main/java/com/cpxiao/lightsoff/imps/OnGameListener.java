package com.cpxiao.lightsoff.imps;

/**
 * OnGameListener
 *
 * @author cpxiao on 2016/5/15.
 */
public interface OnGameListener {
    /**
     * 游戏成功
     */
    void onGameSuccess();

    /**
     * 步数改变
     *
     * @param steps 步数
     */
    void onStepsChange(int steps);

    /**
     * 游戏结束
     */
    void onGameOver();
}
