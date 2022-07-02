import java.awt.*;

/**
 * Created by Julian on 11.03.2015.
 */
public class CollisionDetection {

    private QuadTree g_quadTree = new QuadTree();
    private int maxHeight;
    boolean insertionGrid = true;
    boolean mousePositionGrid = true;

    public CollisionDetection(int maxHeight){

        g_quadTree.setObject("DUMMY");
        this.maxHeight = maxHeight;
    }

    public void insertInTree(Graphics g, boolean drawGrid, Rectangle rectangle1, Rectangle rectangle2, int currentHeight){
        g_quadTree.setEmpty();
        g_quadTree.setObject("DUMMY");
        insertionGrid = drawGrid;
        insertRectangle(g, rectangle1, rectangle2, currentHeight, g_quadTree);
    }

    public Object searchInTree(Graphics g, boolean drawGrid, Point point, Rectangle rectangle2, int currentHeight){
        mousePositionGrid = drawGrid;
        return checkForObjects(g, point, rectangle2, currentHeight, g_quadTree);
    }

    public Object checkForObjects(Graphics g, Point point, Rectangle rectangle2, int currentHeight, QuadTree currentTree){

        if(mousePositionGrid == true)
            g.drawRect(rectangle2.x,rectangle2.y,rectangle2.width,rectangle2.height);

        boolean isSortable = false;

        if(currentTree.getObject() != "DUMMY"){
            return currentTree.getObject();
        }

        if(currentHeight < maxHeight) {
            currentHeight++;

            Rectangle rectangleTopLeft = new Rectangle(rectangle2.x, rectangle2.y, rectangle2.width / 2, rectangle2.height / 2);
            if (rectangleTopLeft.contains(point)) {
                isSortable = true;
                QuadTree quadTree = currentTree.getTopLeftTree();
                if(quadTree != null)
                    return checkForObjects(g, point, rectangleTopLeft, currentHeight, quadTree);
            }

            Rectangle rectangleTopRight = new Rectangle(rectangle2.x + rectangle2.width / 2, rectangle2.y, rectangle2.width / 2, rectangle2.height / 2);
            if (rectangleTopRight.contains(point)) {
                isSortable = true;
                QuadTree quadTree = currentTree.getTopRightTree();
                if(quadTree != null)
                    return checkForObjects(g, point, rectangleTopRight, currentHeight, quadTree);
            }

            Rectangle rectangleBottomLeft = new Rectangle(rectangle2.x, rectangle2.y + rectangle2.height / 2, rectangle2.width / 2, rectangle2.height / 2);
            if (rectangleBottomLeft.contains(point)) {
                isSortable = true;
                QuadTree quadTree = currentTree.getBottomLeftTree();
                if(quadTree != null)
                    return checkForObjects(g, point, rectangleBottomLeft, currentHeight, quadTree);
            }

            Rectangle rectangleBottomRight = new Rectangle(rectangle2.x + rectangle2.width / 2, rectangle2.y + rectangle2.height / 2, rectangle2.width / 2, rectangle2.height / 2);
            if (rectangleBottomRight.contains(point)) {
                isSortable = true;
                QuadTree quadTree = currentTree.getBottomRightTree();
                if(quadTree != null)
                    return checkForObjects(g, point, rectangleBottomRight, currentHeight, quadTree);
            }
        }

        return null;
    }

    public void insertRectangle(Graphics g, Rectangle rectangle1, Rectangle rectangle2, int currentHeight, QuadTree currentTree) {

        if(insertionGrid == true)
            g.drawRect(rectangle2.x,rectangle2.y,rectangle2.width,rectangle2.height);

        boolean isSortable = false;

        if(rectangle1.contains(rectangle2)){
            currentTree.setObject(rectangle1);
            return;
        }

        if(currentHeight < maxHeight){
            currentHeight++;

            Rectangle rectangleTopLeft = new Rectangle(rectangle2.x, rectangle2.y, rectangle2.width/2, rectangle2.height/2);
            if(rectangleTopLeft.intersects(rectangle1)) {           // TopLeft Corner
                isSortable = true;
                QuadTree quadTree = new QuadTree("DUMMY");
                insertRectangle(g, rectangle1, new Rectangle(rectangle2.x, rectangle2.y, rectangle2.width / 2, rectangle2.height / 2), currentHeight, quadTree);
                currentTree.setTopLeftTree(quadTree);
            }

            Rectangle rectangleTopRight = new Rectangle(rectangle2.x+rectangle2.width/2, rectangle2.y, rectangle2.width/2, rectangle2.height/2);
            if(rectangleTopRight.intersects(rectangle1)) {                         // TopRight Corner
                isSortable = true;
                QuadTree quadTree = new QuadTree("DUMMY");
                currentTree.setTopRightTree(quadTree);
                insertRectangle(g, rectangle1, new Rectangle(rectangle2.x + rectangle2.width / 2, rectangle2.y, rectangle2.width / 2, rectangle2.height / 2), currentHeight, quadTree);
            }

            Rectangle rectangleBottomLeft = new Rectangle(rectangle2.x, rectangle2.y+rectangle2.height/2, rectangle2.width/2, rectangle2.height/2);
            if(rectangleBottomLeft.intersects(rectangle1)) {                      //BottomLeft Corner
                isSortable = true;
                QuadTree quadTree = new QuadTree("DUMMY");
                currentTree.setBottomLeftTree(quadTree);
                insertRectangle(g, rectangle1, new Rectangle(rectangle2.x, rectangle2.y + rectangle2.height / 2, rectangle2.width / 2, rectangle2.height / 2), currentHeight, quadTree);
            }

            Rectangle rectangleBottomRight = new Rectangle(rectangle2.x+rectangle2.width/2, rectangle2.y+rectangle2.height/2, rectangle2.width/2, rectangle2.height/2);
            if(rectangleBottomRight.intersects(rectangle1)) {            //BottomRight Corner
                isSortable = true;
                QuadTree quadTree = new QuadTree("DUMMY");
                currentTree.setlBottomRightTree(quadTree);
                insertRectangle(g, rectangle1, new Rectangle(rectangle2.x + rectangle2.width / 2, rectangle2.y + rectangle2.height / 2, rectangle2.width / 2, rectangle2.height / 2), currentHeight, quadTree);
            }

            if(isSortable == false){
                currentTree.setObject(rectangle1);
            }
        }
        else{
            currentTree.setObject(rectangle1);
        }
    }
}
