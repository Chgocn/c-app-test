package io.chgocn.plug.view.listviewswipedelete;

/**
 * 
 * @author yuchentang seperate the group and the child's menu creator
 * 
 */
public interface SwipeMenuExpandableCreator {

    void createGroup(SwipeMenu menu);

    void createChild(SwipeMenu menu);
}
