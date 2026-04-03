import java.util.*;

public class LRU {
    static class Node{
        int key,val;
        Node next,prev;
        Node(int key,int val){
            this.key=key;
            this.val=val;
        }
    }
    static class LRUCache{
        int capacity;
        HashMap<Integer,Node> map;
        Node head,tail;
        public LRUCache(int capacity){
            this.capacity=capacity;
            map=new HashMap<>();
            head=new Node(0,0);
            tail=new Node(0,0);
            head.next=tail;
            tail.prev=head;
        }
        void add(Node node){
            node.next=head.next;
            node.prev=head;
            head.next.prev=node;
            head.next=node;
        }
        void remove(Node node){
            node.next.prev=node.prev;
            node.prev.next=node.next;

        }
        int get(int key){
            if(!map.containsKey(key)){
                return -1;
            }
            Node node=map.get(key);
            remove(node);
            add(node);
            return node.val;
        }
        void display(){
            Node curr = head.next;
            while(curr != tail){
                System.out.print(curr.key + ":" + curr.val + " ");
                curr = curr.next;
            }
            System.out.println();
        }
        void put(int key, int val){
            if(map.containsKey(key)){
                Node node=map.get(key);
                node.val=val;
                remove(node);
                add(node);
                return;
            }
            Node newNode=new Node(key,val);
            map.put(key,newNode);
            add(newNode);
            if(map.size()>capacity){
                Node lru=tail.prev;
                remove(lru);
                map.remove(lru.key);
            }
        }
    }

   public static void main(String []args){
        Scanner in=new Scanner(System.in);
        System.out.println("Enter Capacity of Cache:");
        int cap=in.nextInt();
        LRUCache cache=new LRUCache(cap);
        while(true){
           System.out.println("\n1.Put:\n2.Get:\n3.Exit:");
           System.out.println("\nEnter Choice: ");
           int ch=in.nextInt();
           if(ch==1){
               System.out.println("Enter Key and Value:");
               int key=in.nextInt();
               int val=in.nextInt();
               cache.put(key,val);
               System.out.print("Data in cache: ");
               cache.display();
           }
           else if(ch==2){
               System.out.println("\nEnter Key:");
               int key=in.nextInt();
               int result=cache.get(key);
               if(result==-1)
                   System.out.println("Key not present");
               else
                   System.out.println("Value: "+result);
           }
          else
              break;
        }
        in.close();
   }
}
