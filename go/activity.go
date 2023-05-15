package app

import (
	"context"
	"fmt"
)

func GetBowl(ctx context.Context) error {
	fmt.Println("Getting bowl")
	return nil
}

func PutBowlAwayIfPresent(ctx context.Context) error {
	fmt.Println("Putting bowl away if bowl is out")
	return nil
}

func AddCereal(ctx context.Context) error {
	fmt.Println("Adding cereal")
	return nil
}

func PutCerealBackInBoxIfPresent(ctx context.Context) error {
	fmt.Println("Putting cereal back in box if there is cereal")
	return nil
}

func AddMilk(ctx context.Context) error {
	fmt.Println("Adding milk")
	return nil
}
