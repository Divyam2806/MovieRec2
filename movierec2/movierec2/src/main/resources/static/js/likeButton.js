document.addEventListener("DOMContentLoaded", function() {
    const likeButtons = document.querySelectorAll(".likeButton");

    likeButtons.forEach(button => {
        button.addEventListener("click", function(event) {
            event.preventDefault();

            const movieId = this.getAttribute("data-movie-id");

            fetch("/movies/like", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                body: `movieId=${movieId}`

            })
            .then(response => {
                if (response.ok) {
                    // Handle success (e.g., change button appearance)
                    console.log("Movie liked successfully");
                } else {
                    // Handle error (e.g., show an error message)
                    console.error("Failed to like movie");
                }
            })
            .catch(error => {
                console.error("Error:", error);
            });
        });
    });
});
