function Sleep()
    os.sleep(0.05)
end

function DetDig()
    local det = true
    while det do
        turtle.dig()
        Sleep()
        det = turtle.detect()
    end
end

function DetDigUp()
    if turtle.detectUp() then
        turtle.digUp()
        Sleep()
        return true
    end
    return false
end

function DetDigSides()
    turtle.turnLeft()
    DetDig()
    turtle.turnRight()
    turtle.turnRight()
    DetDig()
    turtle.turnLeft()
end

function DetDigUp()
    if turtle.detectUp() then
        turtle.digUp()
        return true
    end
    return false
end

function FlatDig(totalStep, underBlock)
    function FlatForward()
        DetDigUp()
        DetDigSides()
        DetDig()
    end

    local backCount = 0;
    for _ = 1, totalStep, 1 do
        FlatForward()

        local count = 0
        local inCount = 0
        while true do
            if not turtle.up() then break end
            count = count + 1

            if count >= 1 then FlatForward() end

            if not turtle.detectUp() then
                inCount = inCount + 1
            elseif turtle.detectUp() then
                inCount = 1;
            end

            if inCount >= 10 then break end
        end

        for _ = 1, count, 1 do turtle.down() end

        if underBlock and not turtle.detectDown() then turtle.placeDown() end

        turtle.forward()
        backCount = backCount + 1
    end

    turtle.turnLeft()
    turtle.turnLeft()
    for i = 1, backCount, 1 do
        turtle.forward()
    end
end

-- 첫 시작은 중앙에서 부터 시작해서 x/2 형태로 각각 좌우를 왕복합니다.
-- 이때 2의 배수가 아닐 경우에는 오른쪽으로 치우쳐진 형태로 채굴하며 이동합니다.
-- @param x, y 사각형의 크기
-- @param z 총 전진할 수
function SqDig(x, y, z)
    if z <= 0 then error("Unable to proceed becuse z Must be greater hean 0!") end
    if x <= 1 or y <= 1 then error("Unable to proceed because x, y Must be greater than 1!") end

    local rx = (x % 2 == 0) and (x / 2) or math.floor(x) + 1
    local lx = math.floor(x)

    -- 앞채굴, 앞전진 함수
    function Forward(totalStep)
        for i = 1, totalStep do
            if not turtle.dig() or turtle.forward() then
                return false, i
            end
        end
        return true, -1
    end

    -- 앞으로 전진
    Forward(1)

    turtle.turnLeft()
    Forward(lx)

    for _ = 1, 2 do turtle.turnRight() end
    Forward(rx + lx)

    -- 한번만 채굴하고 본래 위치로 복귀
    if y == 1 then
        for _ = 1, 2 do turtle.turnRight() end
        for _ = 1, rx do turtle.forward() end
        turtle.turnLeft()
        turtle.forward()
        for _ = 1, rx do turtle.turnLeft() end
        os.exit()
    end

    -- 현재 오른쪽으로 바라보고 있으니 정면으로 다시 변환
    turtle.turnLeft()
    Forward(1)
    turtle.turnLeft()

    -- 이 코드 도달시 왼쪽을 바라보고 있다.
    local seeRigth = false
    local seeLeft = true
    local count = 0
    local breakPoint = 0
    for _ = 2, z do
        local step = seeRigth and (rx - 1) or (lx - 1)
        local success, prograss = Forward(step)

        if not success then 
            breakPoint = prograss 
            break
        end

        local turn = seeRigth and (turtle.turnRight) or (turnLeft.turnLeft)
        for _ = 1, 2 do turn() end
        seeRigth = not seeRigth
        seeLeft = not seeLeft
        count = count + 1
    end

    
end
