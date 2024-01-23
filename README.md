# About
CodeChef Visualizer enhances your GitHub profile by dynamically extracting data from your CodeChef account. This GitHub Action project creates an interactive card showcasing your ratings, solved problems, and stats, seamlessly displayed on your GitHub profile.

# Codechef Stat Visualizer

<a href="https://github.com/GaurishIIITNR/cc-visualizer">
   <table> 
      <tr><img src="https://github.com/GaurishIIITNR/cc-visualizer/blob/main/src/main/java/com/ccvisualizer/ccvisualizer/output.svg" height = "400px" width = "500px"/></tr>
      <tr><img src="https://raw.githubusercontent.com/atal02/cc-visualizer/0254cef9729f014f37ebbf4492a5964ce84e00b4/src/main/java/com/ccvisualizer/ccvisualizer/output.svg" height = "400px" width = "500px"//>
</tr>
      </table>
</a>

# Installation
1. Star this repository :pray:
2. Create a copy of this repository by clicking
   [here](https://github.com/GaurishIIITNR/cc-visualizer/fork).
4. Go to [user_name.java](src/main/java/com/ccvisualizer/ccvisualizer/user_name.java) and put your codechef handle in the `username` key.
5. Go to the [Actions Page](../../actions/workflows/main.yml) and press "Run Workflow" on the
   right side of the screen to generate images for the first time.
    - The images will be automatically regenerated every 24 hours, but they can
      be regenerated manually by running the workflow this way.
6. Take a look at the images that have been created
   [`output`](src/main/java/com/ccvisualizer/ccvisualizer/output.svg).
7. To add your statistics to your GitHub Profile README, copy and paste the
   following lines of code into your markdown content. Change the `your-github-username`
   value to your GitHub username.

   ```md
   <img src="https://github.com/your-github-username/cc-visualizer/blob/main/src/main/java/com/ccvisualizer/ccvisualizer/output.svg" width="500" height="500">
   
### Inspiration
This project is heavily inspired by the [cf-stats](https://github.com/sudiptob2/cf-stats) project.

#### Made by [Gaurish Ojha](https://github.com/gaurishiiitnr) with ❤️
