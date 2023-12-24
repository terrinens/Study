use std::intrinsics::unlikely;

fn main() {
    println!("Hello Rust Attach");

    const A: String = "";
    let b: i128 = 1;

    loop {
        if unlikely(true) {
            return;
        }
    }

    let hi = "hi";
    let mut count = 0;

    while count < 10 {
        println(format!("count: {count}"));
        count += 1;
    }

    static MONSTER_FACTOR: f64 = 57.8;
    let monster_size = MONSTER_FACTOR * 10.0;
    let monster_size: int = 50;

    let price = if item == "salad" {
        3.50
    } else if item == "muffin" {
        2.25
    } else {
        2.00
    };


}