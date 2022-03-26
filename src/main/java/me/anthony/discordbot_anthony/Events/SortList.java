/**
 *  Sortlist command.
 *
 *  When a user sends the message "^sort" and the next thing after it is "data_type(int or decimal)", followed by a
 *  list of numbers seperated by commas and no spaces, the bot will display the sort list in ascending order.
 *
 *  To work:
 *      -the list of numbers must be seperated by only commas and have no spaces.
 *      -the list of numbers must be the data type (int or decimal) specified before it.
 *
 *  Example:
 *      user:   ^sort integer -34,3,5,247,-1374,0,27
 *      bot:    Sorted list of integers -1374,-34,0,3,5,27,247
 *
 *
 *  The sorting algorithm 'merge sort' is used to sort the list of numbers
 *
 *  The command "^sort help" will also help the user with this command
 */

package me.anthony.discordbot_anthony.Events;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class SortList extends ListenerAdapter
{
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event)
    {
        String[] messageRecieved = event.getMessage().getContentRaw().split(" ");

        // if the message recieved is from a bot, exit (ignore)
        if(event.getAuthor().isBot())
            return;

        if (messageRecieved[0].equalsIgnoreCase("^sort"))
        {
            if(messageRecieved[1].equalsIgnoreCase("integer"))
            {
                String[] unsortedList = messageRecieved[2].split(",");
                int[] listOfInts = new int[unsortedList.length];

                // parse the String input into an integer array
                for (int i = 0; i < listOfInts.length; i++)
                {
                    try
                    {
                        listOfInts[i] = Integer.parseInt(unsortedList[i]);
                    }
                    catch (IllegalArgumentException e)
                    {
                        event.getChannel().sendMessage("Incorrect data type included.  You selected a sort of integers but " +
                                "instead included at least one other data type that is not int").queue();
                        return;
                    }
                }
                mergeSort(listOfInts);
                event.getChannel().sendMessage(printList(listOfInts, "integer")).queue();
            }
            else if (messageRecieved[1].equalsIgnoreCase("decimal"))
            {
                String[] unsortedList = messageRecieved[2].split(",");
                double[] listOfDoubles = new double[unsortedList.length];

                // parse the String input into an double array
                for (int i = 0; i < listOfDoubles.length; i++)
                {
                    try
                    {
                        listOfDoubles[i] = Double.parseDouble(unsortedList[i]);
                    }
                    catch (IllegalArgumentException e)
                    {
                        event.getChannel().sendMessage("Incorrect data type included.  You selected a sort of decimal numbers (doubles) but " +
                                "instead included at least one other data type that is not a decimal type number").queue();
                        return;
                    }
                }
                mergeSort(listOfDoubles);
                event.getChannel().sendMessage(printList(listOfDoubles, "decimal number")).queue();
            }
            else if (messageRecieved[1].equalsIgnoreCase("help"))
            {
                event.getChannel().sendMessage("To use the ^sort command type:   '^sort data_type list_of_numbers'.\n" +
                        "Data types: 'integer' for positive and negative integers, or 'decimal' for positive and negative numbers with decimals.\n" +
                        "List of numbers: the numbers must be seperated by commas and have NO spaces between them\n" +
                        "Example: ^sort integer -3,12,3455,-123,32,546").queue();
            }
        }
    }

    /**
     * printList method to display a message and the sorted integer list to the user
     * @param list - the sorted integer array
     * @param type - the data type
     * @return - String message
     */
    public String printList(int[] list, String type)
    {
        String message = "Sorted list of "+type+"s: ";
        for (int i=0; i < list.length-1; i++)
        {
            message += list[i] + ", ";
        }
        message += list[list.length-1];
        return message;
    }

    /**
     * printList method to display a message and the sorted double list to the user
     * @param list - the sorted double array
     * @param type - the data type
     * @return - String message
     */
    public String printList(double[] list, String type)
    {
        String message = "Sorted list of "+type+"s: ";
        for (int i=0; i < list.length-1; i++)
        {
            message += list[i] + ", ";
        }
        message += list[list.length-1];
        return message;
    }

    /**
     * Override of sort method, for merge sort
     * @param list - the unsorted array of ints
     */
    public void mergeSort(int[] list)
    {
        int left, right;
        left = 0;
        right = list.length - 1;

        // using sort_p2 because need ints left and right as parameters for recursion
        sort_p2(list, left, right);
    }

    /**
     * Override of sort method, for merge sort
     * @param list - the unsorted array of doubles
     */
    public void mergeSort(double[] list)
    {
        int left, right;
        left = 0;
        right = list.length - 1;

        // using sort_p2 because need ints left and right as parameters for recursion
        sort_p2(list, left, right);
    }

    /**
     * sort_p2 method for the next part of the sorting process which needs int left and right along with the unsorted array
     * @param list - unsorted array of int
     * @param left - int for the first/left index of the array
     * @param right - int for the last/right index of the array
     */
    public void sort_p2(int[] list, int left, int right)
    {
        if (left < right)
        {
            // approx. middle index of the array (integer/floor division)
            int mid = (left + right) / 2;

            // recursive calls
            this.sort_p2(list, left, mid);
            this.sort_p2(list, mid + 1, right);

            // merge sort call
            this.merge(list, left, mid, right);
        }
    }

    /**
     * sort_p2 method for the next part of the sorting process which needs int left and right along with the unsorted array
     * @param list - unsorted array of double
     * @param left - int for the first/left index of the array
     * @param right - int for the last/right index of the array
     */
    public void sort_p2(double[] list, int left, int right)
    {
        if (left < right)
        {
            // approx. middle index of the array (integer/floor division)
            int mid = (left + right) / 2;

            // recursive calls
            this.sort_p2(list, left, mid);
            this.sort_p2(list, mid + 1, right);

            // merge sort call
            this.merge(list, left, mid, right);
        }
    }

    /**
     * merge method to sort an array
     * @param list - unsorted array of int
     * @param left - int for the first/left index of the array
     * @param mid - int for the approx. middle index of the array
     * @param right - int for the last/right index of the array
     */
    public void merge(int[] list, int left, int mid, int right)
    {
        int sub1 = mid - left + 1;
        int sub2 = right - mid;

        // splitting the array into 2
        int L[] = new int[sub1];
        int R[] = new int[sub2];


        // for the left half of the array
        for (int i = 0; i < sub1; i++)
        {
            L[i] = list[left + i];
        }
        // for the right half of the array
        for (int i = 0; i < sub2; i++)
        {
            R[i] = list[mid + i + 1];
        }

        //Initial index for merging
        int i = 0;
        int j = 0;

        //This will represent i + j
        int k = left;


        while(i < sub1 && j < sub2)
        {
            if (L[i] <= R[j])
            {
                list[k] = L[i];
                i++;
            }
            else
            {
                list[k] = R[j];
                j++;
            }
            k++;
        }
        //if i or j do not reach sub1
        while(i < sub1)
        {
            list[k] = L[i];
            i++;
            k++;
        }
        while(j < sub2)
        {
            list[k] = R[j];
            j++;
            k++;
        }
    }

    /**
     * merge method to sort an array
     * @param list - unsorted array of double
     * @param left - int for the first/left index of the array
     * @param mid - int for the approx. middle index of the array
     * @param right - int for the last/right index of the array
     */
    public void merge(double[] list, int left, int mid, int right)
    {
        int sub1 = mid - left + 1;
        int sub2 = right - mid;

        // splitting the array into 2
        double L[] = new double[sub1];
        double R[] = new double[sub2];


        // for the left half of the array
        for (int i = 0; i < sub1; i++)
        {
            L[i] = list[left + i];
        }
        // for the right half of the array
        for (int i = 0; i < sub2; i++)
        {
            R[i] = list[mid + i + 1];
        }

        //Initial index for merging
        int i = 0;
        int j = 0;

        //This will represent i + j
        int k = left;


        while(i < sub1 && j < sub2)
        {
            if (L[i] <= R[j])
            {
                list[k] = L[i];
                i++;
            }
            else
            {
                list[k] = R[j];
                j++;
            }
            k++;
        }
        //if i or j do not reach sub1
        while(i < sub1)
        {
            list[k] = L[i];
            i++;
            k++;
        }
        while(j < sub2)
        {
            list[k] = R[j];
            j++;
            k++;
        }
    }
}
